package com.gmail.picono435.picojobs.hooks;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.economy.PointsImplementation;

public class PlayerPointsHook {
	
	private static boolean isEnabled = false;
	private static PlayerPoints playerPoints;
	
	public static void setupPlayerPoints() {
		if(Bukkit.getPluginManager().getPlugin("PlayerPoints") == null) {
			//PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] The economy plugin PlayerPoints was not found. The POINTS economy will not be enabled.");
			return;
		}
		isEnabled = true;
		//PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] PlayerPoints was found! We are configuring the POINTS economy implementation.");
		hookPlayerPoints();
		PicoJobsAPI.registerEconomy(new PointsImplementation());
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
	
	private static boolean hookPlayerPoints() {
	    final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
	    playerPoints = PlayerPoints.class.cast(plugin);
	    return playerPoints != null; 
	}
	
	public static PlayerPointsAPI getPlayerPointsAPI() {
		return playerPoints.getAPI();
	}
}
