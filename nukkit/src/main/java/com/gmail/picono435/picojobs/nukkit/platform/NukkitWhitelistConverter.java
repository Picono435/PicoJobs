package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;

import java.util.List;

public class NukkitWhitelistConverter implements WhitelistConverter {
    @Override
    public boolean inStringList(Object object, Type type, List<String> whitelist) {
        if(object == null) return true;
        switch(type.getWhitelistType()) {
            case ITEM: {
                return whitelist.contains(((Item) object).getRuntimeEntry().getIdentifier());
            }
            case BLOCK: {
                return whitelist.contains(((Block) object).toItem().getRuntimeEntry().getIdentifier());
            }
            case ENTITY: {
                return whitelist.contains(((Entity) object).getSaveId());
            }
            case DYE: {
                return whitelist.contains(((DyeColor) object).getName().toLowerCase());
            }
        }
        return false;
    }
}
