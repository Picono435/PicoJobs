package com.gmail.picono435.picojobs.api;

import java.util.*;

/**
 * Represents a job type
 * 
 * @author Picono435
 *
 */
public enum Type {
	BREAK("material"),
	KILL("job"),
	FISHING("material"),
	PLACE("material"),
	CRAFT("material"),
	TAME("entity"),
	SHEAR("color"),
	FILL("material"),
	SMELT("material"),
	EAT("material"),
	ENCHANTING("material"),
	REPAIR("material"),
	MILK(""),
	SWIM(""),
	KILL_ENTITY("entity");
	
	private final static Map<String, Type> BY_NAME = new HashMap<String, Type>();
	
	static {
		for(Type type : values()) {
			BY_NAME.put(type.name(), type);
		}
	}
	
	public static Type getType(String name) {
		return BY_NAME.get(name.toUpperCase(Locale.ROOT));
	}

	public static List<Type> getTypes(List<String> names) {
		List<Type> types = new ArrayList<Type>();
		for(String name : names) {
			types.add(getType(name));
		}
		return types;
	}
	
	private final String whitelistType;
	
	private Type(String whitelistType) {
		this.whitelistType = whitelistType;
	}
	
	public String getWhitelistType() {
		return whitelistType;
	}
}
