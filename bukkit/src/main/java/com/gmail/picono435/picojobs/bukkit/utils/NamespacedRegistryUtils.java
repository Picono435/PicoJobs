package com.gmail.picono435.picojobs.bukkit.utils;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is the implementation for {@link NamespacedUtils} that works for
 * minecraft versions 1.14.4 and later
 *
 * @see NamespacedUtils
 * @see NamespacedLegacyUtils
 */
public class NamespacedRegistryUtils extends NamespacedUtils {

    @Override
    public String getKeyFromObject(Object value) {
        if (value instanceof Keyed) {
            return ((Keyed) value).getKey().toString();
        }
        return "minecraft:stone";
    }

    public Material matchMaterial(final String name) {
        return Registry.MATERIAL.match(name);
    }

    public EntityType matchEntityType(final String name) {
        return Registry.ENTITY_TYPE.match(name);
    }

    public Biome matchBiome(final String name) {
        return Registry.BIOME.match(name);
    }

    @Override
    public List<Material> getMaterials() {
        return Registry.MATERIAL.stream().collect(Collectors.toList());
    }

    @Override
    public List<EntityType> getEntityTypes() {
        return Registry.ENTITY_TYPE.stream().collect(Collectors.toList());
    }

    @Override
    public List<Biome> getBiomes() {
        return Registry.BIOME.stream().collect(Collectors.toList());
    }
}
