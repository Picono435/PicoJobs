package com.gmail.picono435.picojobs.bukkit.utils;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MatchUtils {
    public static Map<String, EntityType> entities = new HashMap<>();

    static {
        for(EntityType entity : EntityType.values()) {
            entities.put(entity.name(), entity);
        }
    }

    public static EntityType getEntityByName(String name) {
        Validate.notNull(name, "Name cannot be null");

        String filtered = name;
        if (filtered.startsWith(NamespacedKey.MINECRAFT + ":")) {
            filtered = filtered.substring((NamespacedKey.MINECRAFT + ":").length());
        }

        filtered = filtered.toUpperCase(java.util.Locale.ENGLISH);

        filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");

        if(Bukkit.getServer().getName().equalsIgnoreCase("Mohist"))  {
            try {
                return EntityType.valueOf(filtered);
            } catch(Exception ex) {
                return null;
            }
        }

        return entities.get(name);
    }

    /**
     * Attempts to match the Material with the given name.
     * <player>
     * This is a match lookup; names will be stripped of the "minecraft:"
     * namespace, converted to uppercase, then stripped of special characters in
     * an attempt to format it like the enum.
     *
     * @param name Name of the material to get
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
