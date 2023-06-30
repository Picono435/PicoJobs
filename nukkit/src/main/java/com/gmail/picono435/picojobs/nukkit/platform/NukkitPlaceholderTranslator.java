package com.gmail.picono435.picojobs.nukkit.platform;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NukkitPlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        return PlaceholderAPI.getInstance().translateString(string, PicoJobsNukkit.getInstance().getServer().getPlayer(player).get());
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
