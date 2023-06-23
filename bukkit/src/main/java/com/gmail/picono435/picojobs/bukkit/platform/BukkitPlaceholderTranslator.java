package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.JobPlaceholders;
import com.gmail.picono435.picojobs.bukkit.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

public class BukkitPlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        if(PlaceholderAPIHook.isEnabled()) {
            return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(player), string);
        } else {
            return JobPlaceholders.setPlaceholders(player, string);
        }
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        if(PlaceholderAPIHook.isEnabled()) {
            return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(player), stringList);
        } else {
            return JobPlaceholders.setPlaceholders(player, stringList);
        }
    }
}
