package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
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
            for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
                string = extension.setPlaceholders(player, string);
            }
            return string;
        }
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        if(PlaceholderAPIHook.isEnabled()) {
            return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(player), stringList);
        } else {
            for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
                stringList = extension.setPlaceholders(player, stringList);
            }
            return stringList;
        }
    }
}
