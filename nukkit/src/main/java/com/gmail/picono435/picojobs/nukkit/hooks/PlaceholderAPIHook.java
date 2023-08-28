package com.gmail.picono435.picojobs.nukkit.hooks;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.JobPlayerPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

public class PlaceholderAPIHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(PicoJobsNukkit.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") == null || PicoJobsNukkit.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI").isDisabled()) {
			PicoJobsCommon.getLogger().warn("The recommended dependency PlaceholderAPI was not found. PicoJobs placeholders will not work in other plugins.");
			return;
		}
		isEnabled = true;

		for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
			for (String placeholder : extension.getPlaceholders()) {
				PlaceholderAPI.getInstance().builder(extension.getPrefix() + "_" + placeholder, String.class)
						.visitorLoader(entry -> extension.translatePlaceholders(entry.getPlayer().getUniqueId(), placeholder));
			}
		}
	}

	public static String translateString(String string, Player player) {
		return PlaceholderAPI.getInstance().translateString(string, player);
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
