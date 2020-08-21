package com.gmail.picono435.picojobs.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class SettingsManager {
	
	private PicoJobsPlugin plugin;
	
	private String prefix;
	private String lang;
	private String storageMethod;
	private int saveInterval;
	private int commandAction;
	private Map<String, Integer> allowedCommands = new HashMap<String, Integer>();
	private ConfigurationSection mysqlConfiguration;
	private ConfigurationSection mongodbConfiguration;
	
	public SettingsManager(PicoJobsPlugin plugin) {
		this.plugin = plugin;
		
		reloadConfigurations();
	}
	
	/**
	 * Reload all the configurations
	 * 
	 * @return true if no errors, false if there is errors
	 */
	public boolean reloadConfigurations() {
		FileConfiguration config = this.plugin.getConfig();
		this.prefix = config.getString("prefix");
		this.lang = config.getString("lang");
		this.storageMethod = config.getConfigurationSection("storage").getString("storage-method");
		this.saveInterval = config.getInt("save-interval");
		this.commandAction = config.getInt("jobs-action");
		for(String cmd : config.getConfigurationSection("commands").getKeys(false)) {
			allowedCommands.put(cmd, config.getConfigurationSection("commands").getInt(cmd));
		}
		this.mysqlConfiguration = config.getConfigurationSection("storage").getConfigurationSection("mysql");
		this.mongodbConfiguration = config.getConfigurationSection("storage").getConfigurationSection("mongodb");
		return true;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getLanguage() {
		return lang;
	}
	
	public String getStorageMethod() {
		return storageMethod;
	}
	
	public int getSaveInterval() {
		return saveInterval;
	}
	
	public int getCommandAction() {
		return commandAction;
	}
	
	public Map<String, Integer> getAllowedCommands() {		
		return allowedCommands;
	}
	
	public ConfigurationSection getMySQLConfiguration() {
		return mysqlConfiguration;
	}
	
	public ConfigurationSection getMongoDBConfiguration() {
		return mongodbConfiguration;
	}

	// NOT DEFINED YET
	
	public double getSalaryFrequency() {
		return 0.3D;
	}
	
	public double getMethodFrequency() {
		return 0.3D;
	}
}
