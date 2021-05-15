package com.gmail.picono435.picojobs.storage.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.gmail.picono435.picojobs.api.JobPlayer;

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

}
