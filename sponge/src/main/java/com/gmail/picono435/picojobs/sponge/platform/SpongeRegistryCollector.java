package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.RegistryCollector;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemTypes;

import java.util.List;
import java.util.stream.Collectors;

public class SpongeRegistryCollector implements RegistryCollector {

    @Override
    public List<String> getItemList() {
        return ItemTypes.registry().stream().map(type -> ItemTypes.registry().valueKey(type).asString()).collect(Collectors.toList());
    }

    @Override
    public List<String> getEntityList() {
        return EntityTypes.registry().stream().map(type -> EntityTypes.registry().valueKey(type).asString()).collect(Collectors.toList());
    }
}
