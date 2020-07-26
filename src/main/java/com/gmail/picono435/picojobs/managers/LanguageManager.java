package com.gmail.picono435.picojobs.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.hooks.PlaceholdersHook;

public class LanguageManager {
	
	private static FileConfiguration language;
	private static File language_file;
    
    public static String getMessage(String message) {
    	return getPrefix() + ChatColor.translateAlternateColorCodes('&', language.getString(message));
    }
    
    public static String getMessage(String message, Player p) {
    	return getPrefix() + PlaceholdersHook.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', language.getString(message)));
    }
    
    public static String getPrefix() {
    	return ChatColor.translateAlternateColorCodes('&', PicoJobsPlugin.getPlugin().getConfig().getString("prefix"));
    }
    
    public static void createLanguageFile() {
    	String lang = PicoJobsPlugin.getPlugin().getConfig().getString("lang");
    	language_file = new File(PicoJobsPlugin.getPlugin().getDataFolder(), "langs" + File.separatorChar + lang + ".yml");
        if (!language_file.exists()) {
        	language_file.getParentFile().mkdirs();
        	PicoJobsPlugin.getPlugin().saveResource("langs" + File.separatorChar + lang + ".yml", false);
         }

        language = new YamlConfiguration();
        try {
            language.load(language_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
