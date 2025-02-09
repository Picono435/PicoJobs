package com.gmail.picono435.picojobs.bukkit.utils;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class allows to easily retrieve objects and their {@link org.bukkit.NamespacedKey} representations
 * from the server side registry.
 *
 * @see NamespacedRegistryUtils
 * @see NamespacedLegacyUtils
 */
public abstract class NamespacedUtils {

    /**
     * Returns the String representation of a {@link org.bukkit.NamespacedKey} from the object specified.
     *
     * @param value the object to get the key from
     * @return a string that represents a {@link org.bukkit.NamespacedKey}
     */
    public abstract String getKeyFromObject(final Object value);

    /**
     * Returns the {@link Material} represented by the {@link org.bukkit.NamespacedKey} that the String name represents.
     *
     * @param name the string that represents the {@link org.bukkit.NamespacedKey}
     * @return a {@link Material} that is represented by that {@link org.bukkit.NamespacedKey}
     */
    public abstract Material matchMaterial(final String name);


    /**
     * Returns the {@link EntityType} represented by the {@link org.bukkit.NamespacedKey} that the String name represents.
     *
     * @param name the string that represents the {@link org.bukkit.NamespacedKey}
     * @return a {@link EntityType} that is represented by that {@link org.bukkit.NamespacedKey}
     */
    public abstract EntityType matchEntityType(final String name);

    /**
     * Returns the {@link Biome} represented by the {@link org.bukkit.NamespacedKey} that the String name represents.
     *
     * @param name the string that represents the {@link org.bukkit.NamespacedKey}
     * @return a {@link Biome} that is represented by that {@link org.bukkit.NamespacedKey}
     */
    public abstract Biome matchBiome(final String name);

    public abstract List<Material> getMaterials();

    public abstract List<EntityType> getEntityTypes();

    public abstract List<Biome> getBiomes();

    public List<String> getMaterialKeys() {
        return getMaterials().stream().map(this::getKeyFromObject).collect(Collectors.toList());
    }

    public List<String> getEntityTypeKeys() {
        return getEntityTypes().stream().map(this::getKeyFromObject).collect(Collectors.toList());
    }

    public List<String> getBiomeKeys() {
        return getBiomes().stream().map(this::getKeyFromObject).collect(Collectors.toList());
    }
}
