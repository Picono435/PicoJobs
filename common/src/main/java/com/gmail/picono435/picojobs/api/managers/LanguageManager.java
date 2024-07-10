package com.gmail.picono435.picojobs.api.managers;

import java.util.List;
import java.util.UUID;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import org.spongepowered.configurate.serialize.SerializationException;

// TODO: Create a way to reset admin commands and member commands lang settings
public class LanguageManager {

	/**
	 * Get a message from the language file without using player placeholders
	 * 
	 * @param message the message key
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message) {
    	String chat = FileManager.getLanguageNode().node(message).getString();
    	if(chat == null) {
    		chat = "&cNo message found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(null, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(chat));
    }
    
	/**
	 * Get a message from the language file using player placeholders
	 * 
	 * @param message the message key
	 * @param player the uuid of the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getMessage(String message, UUID player) {
		String chat = FileManager.getLanguageNode().node(message).getString();
		if(chat == null) {
			chat = "&cNo message found in the language file. Please contact an adminstrator of the server.";
		}
    	return getPrefix() + PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(player, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(chat));
    }
    
	/**
	 * Gets the prefix of the plugin declared on the config
	 * 
	 * @return the formatted prefix
	 * @author Picono435
	 *
	 */
    public static String getPrefix() {
    	return PicoJobsCommon.getColorConverter().translateAlternateColorCodes(PicoJobsAPI.getSettingsManager().getPrefix());
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
			message = "&cNo message found in the language file. Please contact an adminstrator of the server.";
    	}
    	return getPrefix() + PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(null, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(message));
    }
    
    /**
	 * Gets a message format from the language config file
	 * 
	 * @param message the message key
	 * @param player the uuid of the player
	 * @return the formatted message value
	 * @author Picono435
	 *
	 */
    public static String getFormat(String message, UUID player) {
		String chat = FileManager.getLanguageNode().node(message).getString();
		if(chat == null) {
			chat = "&cNo message found in the language file. Please contact an adminstrator of the server.";
		}
    	return PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(player, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(chat));
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
       	String chat = FileManager.getLanguageNode().node(time).getString();
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
		List<String> chat = null;
		try {
			chat = FileManager.getLanguageNode().node("aliases", cmd).getList(String.class);
		} catch (SerializationException e) {
			throw new RuntimeException(e);
		}
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
		String chat = FileManager.getLanguageNode().node("aliases", subcmd).getString();
    	if(chat == null) {
    		chat = subcmd;
    	}
    	chat = chat.toLowerCase();
    	return chat;
    }
}
