package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.placeholder.PlaceholderComponent;
import org.spongepowered.api.placeholder.PlaceholderContext;
import org.spongepowered.api.placeholder.PlaceholderParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpongePlaceholderTranslator implements PlaceholderTranslator {

    public static Pattern PLACEHOLDER_PATTERN = Pattern.compile("(?<!((?<!(\\\\))\\\\))[%](?<id>[^%]+_[^%]+)[%]");

    @Override
    public String setPlaceholders(UUID player, String string) {
        Player playerInstance = null;
        if(player != null) {
            playerInstance = PicoJobsSponge.getInstance().getGame().server().player(player).get();
        }
        String newString = string;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(string);
        while(matcher.find()) {
            String placeholder = string.substring(matcher.start(), matcher.end());
            if(PicoJobsSponge.getInstance().getPlaceholderParsers().containsKey(placeholder)) {
                if(playerInstance != null) {
                    newString = newString.replace(placeholder, LegacyComponentSerializer.legacyAmpersand().serialize(PicoJobsSponge.getInstance().getPlaceholderParsers().get(placeholder).parse(PlaceholderContext.builder().associatedObject(playerInstance).build())));
                } else {
                    newString = newString.replace(placeholder, LegacyComponentSerializer.legacyAmpersand().serialize(PicoJobsSponge.getInstance().getPlaceholderParsers().get(placeholder).parse(PlaceholderContext.builder().build())));
                }
            } else {
                PicoJobsCommon.getLogger().warn("Could not find placeholder '" + placeholder + "'.");
            }
        }
        return newString;
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
