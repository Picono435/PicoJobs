package com.gmail.picono435.picojobs.hooks;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.hooks.expansions.JobPlayerExpansion;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "The optional dependency PlaceholderAPI was not found. Some features may not work well!");
			return;
		}
		isEnabled = true;
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
