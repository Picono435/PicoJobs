package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.common.platform.RegistryCollector;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;
import java.util.Locale;

public class ModRegistryCollector implements RegistryCollector {

    @Override
    public List<String> getItemList() {
        return BuiltInRegistries.ITEM.stream().map(item -> BuiltInRegistries.ITEM.getKey(item).toString().toLowerCase(Locale.ROOT)).toList();
    }

    @Override
    public List<String> getEntityList() {
        return BuiltInRegistries.ENTITY_TYPE.stream().map(item -> BuiltInRegistries.ENTITY_TYPE.getKey(item).toString().toLowerCase(Locale.ROOT)).toList();
    }
}
