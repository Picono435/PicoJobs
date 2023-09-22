package com.gmail.picono435.picojobs.api.placeholders;

import com.gmail.picono435.picojobs.api.managers.PlaceholderManager;

import java.util.UUID;

//TODO: This won't be released in the near future
public class JobPlaceholders extends PlaceholderExtension {

    public static final String PREFIX = "job";
    public final static String[] PLACEHOLDERS = {};

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String[] getPlaceholders() {
        return PLACEHOLDERS;
    }

    @Override
    public String translatePlaceholders(UUID player, String identifier) {
        return PlaceholderManager.NULL_PLACEHOLDER;
    }
}
