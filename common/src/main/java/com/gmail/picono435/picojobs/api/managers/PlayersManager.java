package com.gmail.picono435.picojobs.api.managers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.storage.StorageFactory;

public class PlayersManager {
	
	/**
	 * Get the JobPlayer object of a Player, this will only return from cache (online players)
	 * @see PlayersManager#getJobPlayerFromStorage(UUID)
	 * 
	 * @param uuid - the UUID of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(UUID uuid) {
		if(uuid == null) return null;
		JobPlayer jp = PicoJobsAPI.getStorageManager().getCacheManager().getFromCache(uuid);
		if(jp == null) {
			getJobPlayerFromStorage(uuid).thenAcceptAsync(result -> {
				if(result == null) {
					PicoJobsCommon.getLogger().warning("An error occuried while trying to retrieve jobplayer " + uuid + ", the following stack traces will be a consequence of this error.");
				} else {
					PicoJobsAPI.getStorageManager().getCacheManager().addToCache(result);
				}
			});
		}
		return jp;
	}

	/**
	 * Get the JobPlayer object of a Player
	 * It will first try to load from cache and than from storage
	 *
	 * @see PlayersManager#getJobPlayerFromStorage(UUID)
	 *
	 * @param uuid - the UUID of the player
	 * @param wait - if true it will wait to receive the jobplayer from storage if not it will just send what is in the cache
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(UUID uuid, boolean wait) throws ExecutionException, InterruptedException {
		JobPlayer jp = PicoJobsAPI.getStorageManager().getCacheManager().getFromCache(uuid);
		if(jp == null) {
			if(wait) {
				JobPlayer result = getJobPlayerFromStorage(uuid).get();
				if(result == null) {
					PicoJobsCommon.getLogger().warning("An error occuried while trying to retrieve jobplayer " + uuid + ", the following stack traces will be a consequence of this error.");
				} else {
					PicoJobsAPI.getStorageManager().getCacheManager().addToCache(result);
					jp = result;
				}
			} else {
				getJobPlayerFromStorage(uuid).thenAcceptAsync(result -> {
					if(result == null) {
						PicoJobsCommon.getLogger().warning("An error occuried while trying to retrieve jobplayer " + uuid + ", the following stack traces will be a consequence of this error.");
					} else {
						PicoJobsAPI.getStorageManager().getCacheManager().addToCache(result);
					}
				});
			}
		}
		return jp;
	}
	
	/**
	 * Get the JobPlayer object of a Player, this will get from the storage
	 * @see PlayersManager#getJobPlayer(UUID)
	 *
	 * @param uuid - the UUID of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public CompletableFuture<JobPlayer> getJobPlayerFromStorage(UUID uuid) {
		CompletableFuture<JobPlayer> completableFuture = new CompletableFuture<JobPlayer>();
		
		PicoJobsCommon.getSchedulerAdapter().executeAsync(() -> {
			StorageFactory factory = PicoJobsAPI.getStorageManager().getStorageFactory();
			
			try {
				if(!factory.playerExists(uuid)) {
					factory.createPlayer(uuid);
				}
				JobPlayer jp = new JobPlayer(factory.getJob(uuid),
						factory.getMethod(uuid),
						factory.getMethodLevel(uuid),
						factory.getSalary(uuid),
						factory.getSalaryCooldown(uuid),
						factory.getLeaveCooldown(uuid),
						factory.isWorking(uuid),
						uuid);
				completableFuture.complete(jp);
			} catch (Exception ex) {
				PicoJobsCommon.getLogger().severe("Error connecting to the storage. The plugin will not work correctly.");
				ex.printStackTrace();
				completableFuture.cancel(true);
			}
		});
		return completableFuture;
	}
}