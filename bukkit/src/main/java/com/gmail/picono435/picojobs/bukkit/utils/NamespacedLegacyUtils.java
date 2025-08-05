package com.gmail.picono435.picojobs.bukkit.utils;

import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class is the implementation for {@link NamespacedUtils} that works for
 * minecraft versions prior to 1.14.4
 *
 * @see NamespacedUtils
 * @see NamespacedRegistryUtils
 */
public class NamespacedLegacyUtils extends NamespacedUtils {

    public String getKeyFromObject(final Object value) {
        try {
            Method method = value.getClass().getMethod("getKey");
            Object key = method.invoke(value);
            return key.toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return "minecraft:" + ((Enum<?>)value).name().toLowerCase(Locale.ROOT);
        }
    }

    public Material matchMaterial(final String name) {
        return matchObject(name, Material.class);
    }

    public EntityType matchEntityType(final String name) {
        return matchObject(name, EntityType.class);
    }

    public Biome matchBiome(final String name) {
        try {
            Method matchObjectMethod = this.getClass().getMethod("matchObject", String.class, Class.class);
            return (Biome) matchObjectMethod.invoke(null, name, Biome.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Material> getMaterials() {
        return Arrays.asList(Material.values());
    }

    @Override
    public List<EntityType> getEntityTypes() {
        return PicoJobsBukkit.getNamespacedUtils().getEntityTypes();
    }

    @Override
    public List<Biome> getBiomes() {
        return Arrays.asList(Biome.values());
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
