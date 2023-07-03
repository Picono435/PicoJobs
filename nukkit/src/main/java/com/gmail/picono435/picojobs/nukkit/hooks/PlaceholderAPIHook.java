package com.gmail.picono435.picojobs.nukkit.hooks;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.gmail.picono435.picojobs.api.JobPlaceholders;
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

		PlaceholderAPI.getInstance().builder("jobplayer_job", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "job");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_tag", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "tag");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_work", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "work");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_reqmethod", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "reqmethod");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_salary", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "salary");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_level", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "level");
				});

		PlaceholderAPI.getInstance().builder("jobplayer_working", String.class)
				.visitorLoader(entry -> {
					return JobPlaceholders.translatePlaceholders(entry.getPlayer().getUniqueId(), "working");
				});
	}

	public static String translateString(String string, Player player) {
		return PlaceholderAPI.getInstance().translateString(string, player);
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
