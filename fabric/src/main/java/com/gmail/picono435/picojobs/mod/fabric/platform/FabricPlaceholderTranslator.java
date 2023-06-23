package com.gmail.picono435.picojobs.mod.fabric.platform;

import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FabricPlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        return Placeholders.parseText(Component.literal(string), PlaceholderContext.of(PicoJobsMod.getServer().get().getPlayerList().getPlayer(player))).getString();
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        List<String> parsedList = new ArrayList<>();
        for(String s : stringList) {
            parsedList.add(Placeholders.parseText(Component.literal(s), PlaceholderContext.of(PicoJobsMod.getServer().get().getPlayerList().getPlayer(player))).getString());
        }
        return parsedList;
    }
}