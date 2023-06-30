package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class ModWhitelistConverter implements WhitelistConverter {
    @Override
    public boolean inStringList(Object object, Type type, List<String> whitelist) {
        switch(type.getWhitelistType()) {
            case ITEM -> {
                return whitelist.contains(BuiltInRegistries.ITEM.getKey((Item) object).toString());
            }
            case BLOCK -> {
                return whitelist.contains(BuiltInRegistries.BLOCK.getKey((Block) object).toString());
            }
            case ENTITY -> {
                return whitelist.contains(BuiltInRegistries.ENTITY_TYPE.getKey((EntityType<?>) object).toString());
            }
            case DYE -> {
                return whitelist.contains(((DyeColor) object).getName());
            }
        }
        return false;
    }
}
