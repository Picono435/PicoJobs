package com.gmail.picono435.picojobs.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a job type
 * 
 * @author Picono435
 *
 */
public enum Type {
	BREAK("material", "block", "blocks"),
	KILL("job", "job", "kills"),
	FISHING("material", "item", "fish"),
	PLACE("material", "block", "blocks"),
	CRAFT("material", "item", "items"),
	TAME("entity", "entity", "entities"),
	SHEAR("color", "color", "entities"),
	FILL("material", "liquid", "buckets"),
	SMELT("material", "item", "items"),
	EAT("material", "item", "items"),
	ENCHANTING("material", "item", "items"),
	REPAIR("material", "item", "items"),
	MILK("", "", "buckets"),
	KILL_ENTITY("entity", "entity", "kills");
	
	private final static Map<String, Type> BY_NAME = new HashMap<String, Type>();
	
	static {
		for(Type type : values()) {
			BY_NAME.put(type.name(), type);
		}
	}
	
	public static Type getType(String name) {
		return BY_NAME.get(name.toUpperCase(Locale.ROOT));
	}
	
	private String whitelistType;
	private String whitelistConfig;
	private String configMethod;
	
	private Type(String whitelistType, String whitelistConfig, String configMethod) {
		this.whitelistType = whitelistType;
		this.whitelistConfig = whitelistConfig;
		this.configMethod = configMethod;
	}
	
	public String getWhitelistType() {
		return whitelistType;
	}
	
	public String getWhitelistConfig() {
		return whitelistConfig;
	}
	
	public String getConfigMethod() {
		return configMethod;
	}
}
