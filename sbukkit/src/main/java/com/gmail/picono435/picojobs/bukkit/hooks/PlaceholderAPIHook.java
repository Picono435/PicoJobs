package com.gmail.picono435.picojobs.bukkit.hooks;

import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.bukkit.hooks.placeholders.JobPlayerExpansion;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.bukkit.Bukkit;

public class PlaceholderAPIHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			PicoJobsCommon.getLogger().warn("The recommended dependency PlaceholderAPI was not found. PicoJobs placeholders will not work in other plugins.");
			return;
		}
		isEnabled = true;
		new JobPlayerExpansion(PicoJobsBukkit.getInstance()).register();
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
