package com.gmail.picono435.picojobs.api.managers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;

public class LanguageManager {
	
	private static FileConfiguration language;
	private static File language_file;
    
	/**
	 * Get a message from the language file without using player placeholders (PlaceholderAPI)
	 * 
	 * @param message the message key
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholderAPIHook.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
	/**
	 * Get a message from the language file using player placeholders (PlaceholderAPI)
	 * 
	 * @param message the message key
	 * @param p the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message, Player p) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholderAPIHook.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
	/**
	 * Gets the prefix of the plugin declared on the config
	 * 
	 * @return the formatted prefix
	 * @author Picono435
	 *
	 */
    public static String getPrefix() {
    	return ChatColor.translateAlternateColorCodes('&', PicoJobsAPI.getSettingsManager().getPrefix());
    }
    
    /**
	 * Get a message from the language file without using player placeholders (PlaceholderAPI)
	 * 
	 * @param message the message
	 * @return the formatted message
	 * @author Picono435
	 *
	 */
    public static String formatMessage(String message) {
    	if(message == null) {
    		message = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PlaceholderAPIHook.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', message));
    }
    
    /**
	 * Gets a message format from the language config file
	 * 
	 * @param message the message key
	 * @param p the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getFormat(String message, Player p) {
    	String chat = language.getString(message);
    	if(chat == null) {
    		chat = "&cThe asked message was not found in the language file. Please contact an adminstrator of the server.";
    	}
    	return PlaceholderAPIHook.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', chat));
    }
    
    /**
   	 * Gets a time translation from the language config file
   	 * 
   	 * @param time the time key
   	 * @return the translated time value
   	 * @author Picono435
   	 *
   	 */
       public static String getTimeFormat(String time) {
       	String chat = language.getString(time);
       	if(chat == null) {
       		chat = "undefined";
       	}
       	return chat;
       }
    
    /**
	 * Gets a list of all command aliases of a command
	 * 
	 * @param cmd the command
	 * @return the list of aliases
	 * @author Picono435
	 *
	 */
    public static List<String> getCommandAliases(String cmd) {
    	List<String> chat = language.getConfigurationSection("aliases").getStringList(cmd);
    	return chat;
    }
    
    /**
	 * Gets the alias of a subcmd
	 * 
	 * @param subcmd the subcommand
	 * @return the list of aliases
	 * @author Picono435
	 *
	 */
    public static String getSubCommandAlias(String subcmd) {
    	String chat = language.getConfigurationSection("aliases").getString(subcmd);
    	if(chat == null) {
    		chat = subcmd;
    	}
    	chat = chat.toLowerCase();
    	return chat;
    }
    
    /**
	 * Creates and saves thew Language Config File
	 * 
	 * @author Picono435
	 *
	 */
    public static void createLanguageFile() {
    	String lang = PicoJobsAPI.getSettingsManager().getLanguage();
    	language_file = new File(PicoJobsPlugin.getInstance().getDataFolder(), "langs" + File.separatorChar + lang + ".yml");
        if (!language_file.exists()) {
        	language_file.getParentFile().mkdirs();
        	PicoJobsPlugin.getInstance().saveResource("langs" + File.separatorChar + lang + ".yml", false);
         }

        language = new YamlConfiguration();
        try {
            language.load(language_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateFile() {
    	String lang = PicoJobsAPI.getSettingsManager().getLanguage();
    	File old = new File(PicoJobsPlugin.getInstance().getDataFolder(), "langs" + File.separatorChar + lang + "_old.yml");
        language_file.renameTo(old);
        File f = new File(PicoJobsPlugin.getInstance().getDataFolder(), "langs" + File.separatorChar + lang + ".yml");
        PicoJobsPlugin.getInstance().saveResource("langs" + File.separatorChar + lang + ".yml", false);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(f);
        f.delete();
        old.delete();
        
        language.set("admin-commands", null);
        language.set("member-commands", null);
        
        language.setDefaults(fileConfiguration);
        
        language.options().copyDefaults(true);
        
        try {
			language.save(language_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void reloadConfigurations() {
    	language = YamlConfiguration.loadConfiguration(language_file);
    }
}
