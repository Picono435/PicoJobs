package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;
import org.bukkit.DyeColor;

import java.util.*;

public class BukkitWhitelistConverter implements WhitelistConverter {
    @Override
    public boolean inStringList(Object object, Type type, List<String> whitelist) {
        switch(type.getWhitelistType()) {
            case ITEM:
            case BLOCK:
            case ENTITY: {
                return whitelist.contains(PicoJobsBukkit.getNamespacedUtils().getKeyFromObject(object));
            }
            case DYE: {
                return whitelist.contains(((DyeColor) object).name().toLowerCase(Locale.ROOT).toString());
            }
        }
        return false;
    }
}
