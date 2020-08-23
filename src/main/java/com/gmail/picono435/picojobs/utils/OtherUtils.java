package com.gmail.picono435.picojobs.utils;

import org.bukkit.entity.EntityType;

public class OtherUtils {
	
	public static EntityType getEntityByName(String name) {
		name = name.toLowerCase();
        for (EntityType type : EntityType.values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
