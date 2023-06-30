package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.*;

public class BukkitWhitelistConverter implements WhitelistConverter {
    @Override
    public boolean inStringList(Object object, Type type, List<String> whitelist) {
        switch(type.getWhitelistType()) {
            case ITEM:
            case BLOCK: {
                return whitelist.contains(((Material) object).getKey().toString());
            }
            case ENTITY: {
                return whitelist.contains(((EntityType) object).getKey().toString());
            }
            case DYE: {
                return whitelist.contains(((DyeColor) object).name().toLowerCase().toString());
            }
        }
        return false;
    }
}
