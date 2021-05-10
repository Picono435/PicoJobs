package com.gmail.picono435.picojobs.api.managers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.JobPlayer;

public class PlayersManager {
	
	@SuppressWarnings("unused")
	private static PicoJobsPlugin plugin;
	
	public PlayersManager(PicoJobsPlugin main) {
		plugin = main;
	}
	
	/**
	 * Get the JobPlayer object of a Player
	 * 
	 * @param p - the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(Player p) {
		return new JobPlayer(p.getUniqueId());
	}
	
	/**
	 * Get the JobPlayer object of a Player
	 * 
	 * @param uuid - the UUID of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	public JobPlayer getJobPlayer(UUID uuid) {
		return new JobPlayer(uuid);
	}
	
	/**
	 * Get the JobPlayer object of a Player
	 * 
	 * @param name - the name of the player
	 * @return the JobPlayer object, returns null if there is no JobPlayer
	 * @author Picono435
	 */
	@Deprecated
	public JobPlayer getJobPlayer(String name) {
		return new JobPlayer(Bukkit.getOfflinePlayer(name).getUniqueId());
	}
}