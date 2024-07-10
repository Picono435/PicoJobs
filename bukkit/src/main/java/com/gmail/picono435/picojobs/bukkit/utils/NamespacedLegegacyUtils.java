package com.gmail.picono435.picojobs.bukkit.utils;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class NamespacedLegegacyUtils {

    public static String getKeyByEnum(final Enum<?> value) {
        try {
            Method method = value.getClass().getMethod("getKey");
            Object key = method.invoke(value);
            return key.toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return "minecraft:" + value.name().toLowerCase(Locale.ROOT);
        }
    }

    public static Material matchMaterial(final String name) {
        return matchObject(name, Material.class);
    }

    public static EntityType matchEntityType(final String name) {
        return matchObject(name, EntityType.class);
    }

    public static Biome matchBiome(final String name) {
        return matchObject(name, Biome.class);
    }

    public static <E extends Enum<E>> E matchObject(final String name, final Class<E> type) {
        Validate.notNull(name, "Name cannot be null");

        String filtered = name;
        if (filtered.startsWith("minecraft:")) {
            filtered = filtered.substring(("minecraft:").length());
        }

        filtered = filtered.toUpperCase(Locale.ENGLISH);

        filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");

        if(type == Material.class && !Bukkit.getServer().getName().equalsIgnoreCase("Mohist")) {
            return (E) Material.getMaterial(filtered);
        }

        return Enum.valueOf(type, filtered);
    }
}
