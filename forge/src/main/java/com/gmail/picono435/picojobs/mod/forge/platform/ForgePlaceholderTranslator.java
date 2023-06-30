package com.gmail.picono435.picojobs.mod.forge.platform;

import com.gmail.picono435.picojobs.api.JobPlaceholders;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;

import java.util.List;
import java.util.UUID;

public class ForgePlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        return JobPlaceholders.setPlaceholders(player, string);
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        return JobPlaceholders.setPlaceholders(player, stringList);
    }
}