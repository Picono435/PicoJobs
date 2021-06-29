package com.gmail.picono435.picojobs.utils;

import org.bukkit.entity.EntityType;

public class OtherUtils {
	
	public static EntityType getEntityByName(String name) {
		name = name.toLowerCase();
        for (EntityType type : EntityType.values()) {
        	if(name.toLowerCase().startsWith("minecraft:")) {
            	name = name.toLowerCase().replace("minecraft:", "");
            }
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
