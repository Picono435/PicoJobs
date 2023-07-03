package com.gmail.picono435.picojobs.nukkit.platform;

import com.gmail.picono435.picojobs.api.JobPlaceholders;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import com.gmail.picono435.picojobs.nukkit.hooks.PlaceholderAPIHook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NukkitPlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        if(PlaceholderAPIHook.isEnabled()) {
            return PlaceholderAPIHook.translateString(string, PicoJobsNukkit.getInstance().getServer().getPlayer(player).get());
        } else {
            return JobPlaceholders.setPlaceholders(player, string);
        }
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        List<String> parsedList = new ArrayList<>();
        for(String string : stringList) {
            parsedList.add(setPlaceholders(player, string));
        }
        return parsedList;
    }
}
