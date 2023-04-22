package com.gmail.picono435.picojobs.api;

import java.util.Locale;
import java.util.logging.Level;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.managers.JobsManager;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.api.managers.PlayersManager;
import com.gmail.picono435.picojobs.api.managers.SettingsManager;
import com.gmail.picono435.picojobs.storage.StorageManager;

/**
 * Represents the API from PicoJobs plugin
 * 
 * @author Picono435
 *
 */
public class PicoJobsAPI {
	
	private static JobsManager jobsManager = new JobsManager(PicoJobsPlugin.getInstance());
	private static PlayersManager playersManager = new PlayersManager(PicoJobsPlugin.getInstance());
	private static LanguageManager languageManager = new LanguageManager();
	private static SettingsManager settingsManager = new SettingsManager(PicoJobsPlugin.getInstance());
	private static StorageManager storageManager = new StorageManager();
	
	/**
	 * Use this method to get the JobsManager of the plugin, with it you can edit/get most of the things of a job. 
	 * 
	 * @return The jobs manager of the plugin
	 * @author Picono435
	 */
	public static JobsManager getJobsManager() {
		return jobsManager;
	}
	
	/**
	 * Use this method to get the PlayersManager of the plugin, with it you can edit and get most of the data of a player.
	 * 
	 * @return The players manager of the plugin
	 * @author Picono435
	 */
	public static PlayersManager getPlayersManager() {
		return playersManager;
	}
	
	/**
	 * Use this method to get the LanguageManager of the plugin, with it you can get all the messages of the plugin.
	 * 
	 * @return The language manager of the plugin
	 * @author Picono435
	 */
	public static LanguageManager getLanguageManager() {
		return languageManager;
	}
	
	/**
	 * Use this method to get the SettingsManager of the plugin, with it you can access some configurations of the plugin.
	 * 
	 * @return The settings manager of the plugin
	 * @author Picono435
	 */
	public static SettingsManager getSettingsManager() {
		return settingsManager;
	}
	
	/**
	 * Use this method to get the StorageManager of the plugin, with it you can save and delete data of the plugin.
	 * 
	 * @return The storage manager of the plugin
	 * @author Picono435
	 */
	public static StorageManager getStorageManager() {
		return storageManager;
	}
	
	/**
	 * Registers a Economy Implementation
	 * 
	 * @param economy the economy implementation
	 * @return whether is successful or not
	 * @author Picono435
	 */
	public static boolean registerEconomy(EconomyImplementation economy) {
		if(economy.getRequiredPlugin() == null) return false;
		PicoJobsPlugin.getInstance().economies.put(economy.getName().toUpperCase(Locale.ROOT), economy);
		PicoJobsPlugin.getInstance().getLogger().info("Registered " + economy.getName().toUpperCase(Locale.ROOT) + " economy implementation.");
		return true;
	}

	/**
	 * Registers a Work Zone Implementation
	 *
	 * @param workZone the work zone implementation
	 * @return whether is successful or not
	 * @author Picono435
	 */
	public static boolean registerWorkZone(WorkZoneImplementation workZone) {
		if(workZone.getRequiredPlugin() == null) return false;
		PicoJobsPlugin.getInstance().workZones.put(workZone.getName().toUpperCase(Locale.ROOT), workZone);
		PicoJobsPlugin.getInstance().getLogger().info("Registered " + workZone.getName().toUpperCase(Locale.ROOT) + " work zone implementation.");
		return true;
	}

	/**
	 * Gets the work zone implementation by name
	 *
	 * @param name the name of the work zone
	 * @return the work zone identified by that name
	 * @author Picono435
	 */
	public static WorkZoneImplementation getWorkZone(String name) {
		return PicoJobsPlugin.getInstance().workZones.get(name);
	}
}
