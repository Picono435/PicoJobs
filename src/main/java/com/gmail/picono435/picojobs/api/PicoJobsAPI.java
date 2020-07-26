package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.JobsManager;
import com.gmail.picono435.picojobs.managers.PlayersManager;

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
}
