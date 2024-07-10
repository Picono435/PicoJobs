package com.gmail.picono435.picojobs.bukkit.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			PicoJobsCommon.getLogger().warn("The recommended dependency PlaceholderAPI was not found. PicoJobs placeholders will not work in other plugins.");
			return;
		}
		isEnabled = true;
		for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
			new PlaceholderExpansion() {
				@Override
				public String getIdentifier() {
					return extension.getPrefix();
				}

				@Override
				public String getAuthor() {
					return "Picono435";
				}

				@Override
				public String getVersion() {
					return PicoJobsBukkit.getInstance().getDescription().getVersion();
				}

				@Override
				public String onPlaceholderRequest(Player player, String identifier) {
					return extension.translatePlaceholders(player.getUniqueId(), identifier);
				}

				@Override
				public boolean persist(){
					return true;
				}

				@Override
				public boolean canRegister(){
					return true;
				}
			}.register();
		}
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
