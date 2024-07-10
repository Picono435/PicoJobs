package com.gmail.picono435.picojobs.mod.forge.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.JobPlayerPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;

import java.util.List;
import java.util.UUID;

public class ForgePlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
            string = extension.setPlaceholders(player, string);
        }
        return string;
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
            stringList = extension.setPlaceholders(player, stringList);
        }
        return stringList;
    }
}