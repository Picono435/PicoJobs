package com.gmail.picono435.picojobs.api;

import java.util.Locale;

import com.gmail.picono435.picojobs.api.managers.JobsManager;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.api.managers.PlayersManager;
import com.gmail.picono435.picojobs.api.managers.SettingsManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.storage.StorageManager;

/**
 * Represents the API from PicoJobs plugin
 * 
 * @author Picono435
 *
 */
public class PicoJobsAPI {
	
	private static JobsManager jobsManager = new JobsManager();
	private static PlayersManager playersManager = new PlayersManager();
	private static LanguageManager languageManager = new LanguageManager();
	private static SettingsManager settingsManager = new SettingsManager();
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
		if(!PicoJobsCommon.getPlatformAdapter().isPluginEnabled(economy.getRequiredPlugin())) return false;
		PicoJobsCommon.getMainInstance().economies.put(economy.getName().toUpperCase(Locale.ROOT), economy);
		PicoJobsCommon.getLogger().info("Registered " + economy.getName().toUpperCase(Locale.ROOT) + " economy implementation.");
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
		if(!PicoJobsCommon.getPlatformAdapter().isPluginEnabled(workZone.getRequiredPlugin())) return false;
		PicoJobsCommon.getMainInstance().workZones.put(workZone.getName().toUpperCase(Locale.ROOT), workZone);
		PicoJobsCommon.getLogger().info("Registered " + workZone.getName().toUpperCase(Locale.ROOT) + " work zone implementation.");
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
		return PicoJobsCommon.getMainInstance().workZones.get(name);
	}
}
