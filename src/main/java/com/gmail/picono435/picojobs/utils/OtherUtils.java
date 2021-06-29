package com.gmail.picono435.picojobs.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
	
	/**
     * Attempts to match the Material with the given name.
     * <p>
     * This is a match lookup; names will be stripped of the "minecraft:"
     * namespace, converted to uppercase, then stripped of special characters in
     * an attempt to format it like the enum.
     *
     * @param name Name of the material to get
     * @param legacyName whether this is a legacy name (see
     * {@link #getMaterial(java.lang.String, boolean)}
     * @return Material if found, or null
     */
    public static Material matchMaterial(final String name) {
        Validate.notNull(name, "Name cannot be null");

        String filtered = name;
        if (filtered.startsWith(NamespacedKey.MINECRAFT + ":")) {
            filtered = filtered.substring((NamespacedKey.MINECRAFT + ":").length());
        }

        filtered = filtered.toUpperCase(java.util.Locale.ENGLISH);

        filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
        
        if(Bukkit.getServer().getName().equalsIgnoreCase("Mohist"))  {
        	try {
        		return Material.valueOf(filtered);
        	} catch(Exception ex) {
        		return null;
        	}
        }
        
        return Material.getMaterial(filtered);
    }
}
