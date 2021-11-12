package com.gmail.picono435.picojobs.api.managers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.StorageFactory;

public class PlayersManager {
	
	@SuppressWarnings("unused")
	private static PicoJobsPlugin plugin;
	
	public PlayersManager(PicoJobsPlugin main) {
		plugin = main;
	}
	
	/**
	 * Get the JobPlayer object of a Player, this will only return from cache (online players)
	 * @see PlayersManager#getJobPlayerFromStorage(UUID)
	 * 
	 * @param p - the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(Player p) {
		PicoJobsPlugin.getInstance().debugMessage("Retrieving player: N: " + p.getName() + " U: " + p.getUniqueId());
		JobPlayer jp = PicoJobsAPI.getStorageManager().getCacheManager().getFromCache(p.getUniqueId());
		if(jp == null) {
			PicoJobsPlugin.getInstance().debugMessage("An error occuried while retrieving the jobplayer: N: " + p.getName() + " U: " + p.getUniqueId());
			getJobPlayerFromStorage(p.getUniqueId()).thenAcceptAsync(result -> {
				if(result == null) {
					PicoJobsPlugin.getInstance().getLogger().log(Level.WARNING, "An error occuried while trying to retrieve jobplayer " + p.getUniqueId() + ", the following stack traces will be consequence of this error.");
				} else {
					PicoJobsAPI.getStorageManager().getCacheManager().addToCache(result);
				}
			});
		}
		return jp;
	}
	
	/**
	 * Get the JobPlayer object of a Player, this will only return from cache (online players)
	 * @see PlayersManager#getJobPlayerFromStorage(UUID)
	 * 
	 * @param uuid - the UUID of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(UUID uuid) {
		JobPlayer jp = PicoJobsAPI.getStorageManager().getCacheManager().getFromCache(uuid);
		if(jp == null) {
			getJobPlayerFromStorage(uuid).thenAcceptAsync(result -> {
				if(result == null) {
					PicoJobsPlugin.getInstance().getLogger().log(Level.WARNING, "An error occuried while trying to retrieve jobplayer " + uuid + ", the following stack traces will be consequence of this error.");
				} else {
					PicoJobsAPI.getStorageManager().getCacheManager().addToCache(result);
				}
			});
		}
		return jp;
	}
	
	/**
	 * Get the JobPlayer object of a Player, this will only return from cache (online players)
	 * @see PlayersManager#getJobPlayerFromStorage(UUID)
	 * 
	 * @param name - the name of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	@Deprecated
	public JobPlayer getJobPlayer(String name) {
		JobPlayer jp = PicoJobsAPI.getStorageManager().getCacheManager().getFromCache(Bukkit.getOfflinePlayer(name).getUniqueId());
		return jp;
	}
	
	/**
	 * Get the JobPlayer object of a Player, this will get from the storage
	 * @see PlayersManager#getJobPlayer(Player)
	 * @see PlayersManager#getJobPlayer(UUID)
	 * @see PlayersManager#getJobPlayer(String)
	 * 
	 * @param uuid - the UUID of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public CompletableFuture<JobPlayer> getJobPlayerFromStorage(UUID uuid) {
		CompletableFuture<JobPlayer> completableFuture = new CompletableFuture<JobPlayer>();
		
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			StorageFactory factory = PicoJobsAPI.getStorageManager().getStorageFactory();
			
			try {
				if(!factory.playerExists(uuid)) {
					factory.createPlayer(uuid);
				}
				JobPlayer jp = new JobPlayer(PicoJobsAPI.getJobsManager().getJob(factory.getJob(uuid)),
						factory.getMethod(uuid),
						factory.getMethodLevel(uuid),
						factory.getSalary(uuid), 
						factory.isWorking(uuid),
						uuid);
				completableFuture.complete(jp);
			} catch (Exception ex) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, "Error connecting to the storage. The plugin will not work correctly.");
				ex.printStackTrace();
				completableFuture.cancel(true);
			}
		});
		return completableFuture;
	}
}