package com.gmail.picono435.picojobs.hooks;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.expansions.JobPlayerExpansion;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIHook {
	
	private static boolean isEnabled = false;
	
	public static void setupPlaceholderAPI() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "The recommended dependency PlaceholderAPI was not found. Some features may not work well!");
			return;
		}
		isEnabled = true;
		new JobPlayerExpansion(PicoJobsPlugin.getInstance()).register();
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
	
	public static String setPlaceholders(Player p, String message) {
		if(!isEnabled) {
			if(p == null) return message;
			String[] identifiers = StringUtils.substringsBetween(message, "%", "%");
			if(identifiers == null) return message;
			for(String identifier : identifiers) {
				String defaultIdentifier =  "%" + identifier + "%";
				if(!identifier.startsWith("jobplayer_")) continue;
				identifier = identifier.replaceFirst("jobplayer_", "");
				message = message.replace(defaultIdentifier, translatePlaceholders(p, identifier)); 
			}
			return message;
		}
		return PlaceholderAPI.setPlaceholders(p, message);
	}
	
	public static List<String> setPlaceholders(Player p, List<String> messages) {
		if(!isEnabled) {
			if(p == null) return messages;
			List<String> newMessages = new ArrayList<String>();
			for(String message : messages) {
				String[] identifiers = StringUtils.substringsBetween(message, "%", "%");
				if(identifiers != null) {
					for(String identifier : identifiers) {
						String defaultIdentifier =  "%" + identifier + "%";
						if(!identifier.startsWith("jobplayer_")) continue;
						identifier = identifier.replaceFirst("jobplayer_", "");
						message = message.replace(defaultIdentifier, translatePlaceholders(p, identifier));
					}
				}
				newMessages.add(message);
			}
			return newMessages;
		}
		return PlaceholderAPI.setPlaceholders(p, messages);
	}
	
	public static String translatePlaceholders(Player p, String identifier) {
		NumberFormat df = NumberFormat.getNumberInstance(Locale.getDefault());
		
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
    	
        if(identifier.equals("job")) {
            if(!jp.hasJob()) {
            	return LanguageManager.getFormat("none-format", p);
            }
            return jp.getJob().getDisplayName();
        }
        
        if(identifier.equals("work")) {
        	Job job = jp.getJob();
        	if(job == null) {
        		return LanguageManager.getFormat("none-format", p);
        	}
        	double level = jp.getMethodLevel();
        	int reqmethod = (int) (job.getMethod() * level * job.getMethodFrequency());
        	double value = reqmethod - jp.getMethod();
        	String workMessage = job.getWorkMessage();
        	workMessage = workMessage.replace("%a%", df.format(value));
        	return workMessage;
        }
        
        if(identifier.equals("salary")) {
            return df.format(Math.round(jp.getSalary()));
        }
        
        if(identifier.equals("working")) {
        	return jp.isWorking() + "";
        }

        return "[NULL_PLACEHOLDER]";
	}
}
