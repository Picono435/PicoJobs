package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.JobsManager;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.managers.PlayersManager;
import com.gmail.picono435.picojobs.managers.SettingsManager;
import com.gmail.picono435.picojobs.managers.StorageManager;

public class PicoJobsAPI {
	
	/**
	 * Use this method to get the JobsManager of the plugin, with it you can edit/get most of the things of a job. 
	 * 
	 * @return The jobs manager of the plugin
	 * @author Picono435
	 */
	public static JobsManager getJobsManager() {
		return new JobsManager(PicoJobsPlugin.getInstance());
	}
	
	/**
	 * Use this method to get the PlayersManager of the plugin, with it you can edit and get most of the data of a player.
	 * 
	 * @return The players manager of the plugin
	 * @author Picono435
	 */
	public static PlayersManager getPlayersManager() {
		return new PlayersManager(PicoJobsPlugin.getInstance());
	}
	
	/**
	 * Use this method to get the LanguageManager of the plugin, with it you can get all the messages of the plugin.
	 * 
	 * @return The language manager of the plugin
	 * @author Picono435
	 */
	public static LanguageManager getLanguageManager() {
		return new LanguageManager();
	}
	
	/**
	 * Use this method to get the SettingsManager of the plugin, with it you can access some configurations of the plugin.
	 * 
	 * @return The settings manager of the plugin
	 * @author Picono435
	 */
	public static SettingsManager getSettingsManager() {
		return new SettingsManager();
	}
	
	/**
	 * Use this method to get the StorageManager of the plugin, with it you can save and delete data of the plugin.
	 * 
	 * @return The storage manager of the plugin
	 * @author Picono435
	 */
	public static StorageManager getStorageManager() {
		return new StorageManager();
	}
}
