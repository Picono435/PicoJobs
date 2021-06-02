package com.gmail.picono435.picojobs.api;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Represents a storage method
 * 
 * @author Picono435
 *
 */
public enum StorageMethod {
	MYSQL(),
	MONGODB(),
	YAML();
	
	private final static Map<String, StorageMethod> BY_NAME = Maps.newHashMap();
	
	public static StorageMethod getStorageMethod(String name) {
		return BY_NAME.get(name.toUpperCase(Locale.ROOT));
	}
	
	static {
		for(StorageMethod type : values()) {
			BY_NAME.put(type.name(), type);
		}
	}
}