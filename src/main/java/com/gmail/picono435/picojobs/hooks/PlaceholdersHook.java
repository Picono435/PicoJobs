package com.gmail.picono435.picojobs.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class PlaceholdersHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] The optional dependency PlaceholderAPI was not found. Some features may not work well!");
			return;
		}
		isEnabled = true;
		PicoJobsPlugin.sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] PlaceholderAPI was found! We are configuring the connection between us and PlaceholderAPI.");
		new JobPlayerExpansion(PicoJobsPlugin.getInstance()).register();
	}
	
	public static String setPlaceholders(Player p, String message) {
		if(!isEnabled) {
			return message;
		}
		return PlaceholderAPI.setPlaceholders(p, message);
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
