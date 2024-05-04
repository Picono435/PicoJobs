package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.ItemID;
import com.gmail.picono435.picojobs.common.platform.RegistryCollector;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class NukkitRegistryCollector implements RegistryCollector {

    private static Field shortNamesField;

    static {
        try {
            shortNamesField = Entity.class.getDeclaredField("shortNames");
            shortNamesField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getItemList() {
        return Arrays.stream(ItemID.class.getFields()).map(field -> "minecraft:" + field.getName().toLowerCase(Locale.ROOT)).collect(Collectors.toList());
    }

    @Override
    public List<String> getEntityList() {
        Map<String, String> shortNames;
        try {
            shortNames = (Map<String, String>) shortNamesField.get(null);
        } catch (IllegalAccessException e) {
            shortNames = new HashMap<>();
        }
        return new ArrayList<>(shortNames.values());
    }
}
