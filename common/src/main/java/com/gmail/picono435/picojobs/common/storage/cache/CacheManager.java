package com.gmail.picono435.picojobs.common.storage.cache;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;

import java.util.*;

public class CacheManager {

	private Map<UUID, JobPlayer> cache = new HashMap<UUID, JobPlayer>();


	/**
	 * Checks if the player exists in the cache
	 * 
	 * @param uuid the unique ID of the player
	 * @return true if the player exists, else false
	 */
	public boolean playerExists(UUID uuid) {
		return cache.containsKey(uuid);
	}
	
	/**
	 * Adds a job player to the cache
	 * 
	 * @param jp the job player to add to cache
	 * @return the job player that was added to cache
	 */
	public JobPlayer addToCache(JobPlayer jp) {
		PicoJobsCommon.getLogger().debug("Added JobPlayer to cache: U: " + jp.getUUID());
		return cache.put(jp.getUUID(), jp);
	}
	
	/**
	 * Gets a job player from the cache
	 * 
	 * @param uuid the unique ID of the player
	 * @return the job player that was got from the cache
	 */
	public JobPlayer getFromCache(UUID uuid) {
		return cache.get(uuid);
	}
	
	/**
	 * Removes data from a player from the cache
	 * 
	 * @param uuid the player to remove from cache
	 */
	public void removeFromCache(UUID uuid) {
		cache.remove(uuid);
	}

	/**
	 * Gets all players from the cache system
	 *
	 * @return the job player uuid list of all jobplayers cached
	 */
	public List<UUID> getAllFromCache() {
		return new ArrayList<>(cache.keySet());
	}

	/**
	 * Clears all the cache saved in the plugin, make sure you know what you doing before calling this
	 */
	public void clearCache() {
		cache.clear();
	}

}
