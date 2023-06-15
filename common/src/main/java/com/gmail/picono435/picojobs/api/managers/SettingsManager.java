package com.gmail.picono435.picojobs.api.managers;

import java.util.HashMap;
import java.util.Map;

import com.gmail.picono435.picojobs.common.file.FileManager;
import org.spongepowered.configurate.ConfigurationNode;

public class SettingsManager {

	private String prefix;
	private String lang;
	private String storageMethod;
	private boolean automaticData;
	private int commandAction;
	private Map<String, Integer> allowedCommands = new HashMap<String, Integer>();
	private ConfigurationNode remoteSqlConfiguration;
	private ConfigurationNode mongodbConfiguration;
	private int salaryCooldown;
	private int leaveCooldown;
	private boolean autoWorking;
	private boolean resetCacheOnJoin;
	
	public SettingsManager() {
		reloadConfigurations();
	}
	
	/**
	 * Reload all the configurations
	 * 
	 * @return true if no errors, false if there is errors
	 */
	public boolean reloadConfigurations() {
		ConfigurationNode configNode = FileManager.getConfigNode();
		this.prefix = configNode.node("prefix").getString();
		this.lang = configNode.node("lang").getString();
		this.storageMethod = configNode.node("storage", "storage-method").getString();
		this.commandAction = configNode.node("jobs-action").getInt();
		for(Object cmd : configNode.node("commands").childrenMap().keySet()) {
			allowedCommands.put((String)cmd, configNode.node("commands", cmd).getInt());
		}
		this.remoteSqlConfiguration = configNode.node("storage", "remote-sql");
		this.mongodbConfiguration = configNode.node("storage", "mongodb");
		this.salaryCooldown = configNode.node("salary-cooldown").getInt();
		this.leaveCooldown = configNode.node("leave-cooldown").getInt();
		this.autoWorking = configNode.node("auto-working").getBoolean();
		this.resetCacheOnJoin = configNode.node("storage", "reset-cache-on-join").getBoolean();
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
	
	public int getCommandAction() {
		return commandAction;
	}
	
	public Map<String, Integer> getAllowedCommands() {		
		return allowedCommands;
	}
	
	public ConfigurationNode getRemoteSqlConfiguration() {
		return remoteSqlConfiguration;
	}
	
	public ConfigurationNode getMongoDBConfiguration() {
		return mongodbConfiguration;
	}
	
	public int getSalaryCooldown() {
		return salaryCooldown;
	}

	public int getLeaveCooldown() {
		return leaveCooldown;
	}

	public boolean isAutoWorking() {
		return autoWorking;
	}

	public boolean isResetCacheOnJoin() {
		return resetCacheOnJoin;
	}
}
