package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.List;

public class SpongeWhitelistConverter implements WhitelistConverter {

    @Override
    public boolean inStringList(Object object, Type type, List<String> whitelist) {
        switch(type.getWhitelistType()) {
            case ITEM: {
                return whitelist.contains(((ItemType) object).key(RegistryTypes.ITEM_TYPE).asString());
            }
            case BLOCK: {
                return whitelist.contains(((BlockType) object).key(RegistryTypes.BLOCK_TYPE).asString());
            }
            case ENTITY: {
                return whitelist.contains(((EntityType<?>) object).key(RegistryTypes.ENTITY_TYPE).asString());
            }
            case DYE: {
                return whitelist.contains(((DyeColor) object).key(RegistryTypes.DYE_COLOR).asString().toLowerCase());
            }
        }
        return false;
    }
}
