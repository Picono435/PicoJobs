package com.gmail.picono435.picojobs.api.placeholders;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PlaceholderExtension {

    public String setPlaceholders(UUID player, String message) {
        if(player == null) return message;
        String[] identifiers = StringUtils.substringsBetween(message, "%", "%");
        if(identifiers == null) return message;
        for(String identifier : identifiers) {
            String defaultIdentifier =  "%" + identifier + "%";
            if(!identifier.startsWith(getPrefix() + "_")) continue;
            identifier = identifier.replaceFirst(getPrefix() + "_", "");
            message = message.replace(defaultIdentifier, translatePlaceholders(player, identifier));
        }
        return message;
    }

    public List<String> setPlaceholders(UUID player, List<String> messages) {
        if(player == null) return messages;
        List<String> newMessages = new ArrayList<String>();
        for(String message : messages) {
            newMessages.add(setPlaceholders(player, message));
        }
        return newMessages;
    }

    public abstract String getPrefix();

    public abstract String[] getPlaceholders();

    public abstract String translatePlaceholders(UUID player, String identifier);
}
