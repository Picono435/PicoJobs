package com.gmail.picono435.picojobs.mod.fabric.platform;

import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FabricPlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        String toParseString = string;
        Pattern pattern = Pattern.compile("(?<!((?<!(\\\\))\\\\))[%](?<id>[^%]+_[^%]+)[%]");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String id = matcher.group("id");
            if(id.equals("cooldown_time") || id.equals("cooldown_mtime")) continue;
            toParseString = toParseString.replace("%" + id + "%", "%" + id.replace("_", ":") + "%");
        }
        return Placeholders.parseText(Component.literal(toParseString), PlaceholderContext.of(PicoJobsMod.getServer().get().getPlayerList().getPlayer(player)), Pattern.compile("(?<!((?<!(\\\\))\\\\))[%](?<id>[^%]+:[^%]+)[%]")).getString();

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