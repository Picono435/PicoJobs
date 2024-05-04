package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.common.platform.RegistryCollector;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BukkitRegistryCollector implements RegistryCollector {

    @Override
    public List<String> getItemList() {
        return Arrays.stream(Material.values()).map(material -> "minecraft:" + material.name().toLowerCase(Locale.ROOT)).toList();
    }

    @Override
    public List<String> getEntityList() {
        return Arrays.stream(EntityType.values()).map(material -> "minecraft:" + (material.getName() == null ? "unknown" : material.getName().toLowerCase(Locale.ROOT))).toList();
    }
}
