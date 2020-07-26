package com.gmail.picono435.picojobs.managers;

import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class PlayersManager {
	
	@SuppressWarnings("unused")
	private static PicoJobsPlugin plugin;
	
	public PlayersManager(PicoJobsPlugin main) {
		plugin = main;
	}
	
	/**
	 * Check if the player has a job or not.
	 * 
	 * @param p - the player
	 * @return true if he has a job
	 * @author Picono435
	 */
	public boolean hasJob(Player p) {
		return PicoJobsPlugin.jobs.containsKey(p.getName());
	}
	/**
	 * Check if the player has a job or not.
	 * 
	 * @param p - the player name
	 * @return true if he has a job
	 * @author Picono435
	 */
	public boolean hasJob(String playername) {
		playername = playername.toLowerCase();
		return PicoJobsPlugin.jobs.containsKey(playername);
	}
}