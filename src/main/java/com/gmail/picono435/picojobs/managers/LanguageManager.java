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
    
	/**
	 * Get a message from the language file without using player placeholders (PlaceholderAPI)
	 * 
	 * @param message - the message key
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholdersHook.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
	/**
	 * Get a message from the language file using player placeholders (PlaceholderAPI)
	 * 
	 * @param message - the message key
	 * @param p - the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message, Player p) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholdersHook.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
	/**
	 * Gets the prefix of the plugin declared on the config
	 * 
	 * @return the formatted prefix
	 * @author Picono435
	 *
	 */
    public static String getPrefix() {
    	return ChatColor.translateAlternateColorCodes('&', PicoJobsPlugin.getPlugin().getConfig().getString("prefix"));
    }
    
    /**
	 * Get a message from the language file without using player placeholders (PlaceholderAPI)
	 * 
	 * @param message - the message
	 * @return the formatted message
	 * @author Picono435
	 *
	 */
    public static String formatMessage(String message) {
    	if(message == null) {
    		message = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholdersHook.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', message));
    }
    
    /**
	 * Gets a message format from the language config file
	 * 
	 * @param message - the message key
	 * @param p - the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getFormat(String message, Player p) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return PlaceholdersHook.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
    /**
	 * Creates and saves thew Language Config File
	 * 
	 * @author Picono435
	 *
	 */
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
