package com.gmail.picono435.picojobs.api;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Represents a job type
 * 
 * @author Picono435
 *
 */
public enum Type {
	BREAK(),
	KILL(),
	FISHING(),
	PLACE();
	
	private final static Map<String,  Type> BY_NAME = Maps.newHashMap();
	
	public static Type getType(String name) {
		return BY_NAME.get(name);
	}
	
	static {
		for(Type type : values()) {
			BY_NAME.put(type.name(), type);
		}
	}
}
