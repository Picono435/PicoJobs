package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PlaceholderHook {

    public static String setPlaceholders(UUID player, String message) {
        if(player == null) return message;
        String[] identifiers = StringUtils.substringsBetween(message, "%", "%");
        if(identifiers == null) return message;
        for(String identifier : identifiers) {
            String defaultIdentifier =  "%" + identifier + "%";
            if(!identifier.startsWith("jobplayer_")) continue;
                identifier = identifier.replaceFirst("jobplayer_", "");
                message = message.replace(defaultIdentifier, translatePlaceholders(player, identifier));
            }
        return message;
    }

    public static List<String> setPlaceholders(UUID player, List<String> messages) {
        if(player == null) return messages;
        List<String> newMessages = new ArrayList<String>();
        for(String message : messages) {
            String[] identifiers = StringUtils.substringsBetween(message, "%", "%");
            if(identifiers != null) {
                for(String identifier : identifiers) {
                    String defaultIdentifier =  "%" + identifier + "%";
                    if(!identifier.startsWith("jobplayer_")) continue;
                    identifier = identifier.replaceFirst("jobplayer_", "");
                    message = message.replace(defaultIdentifier, translatePlaceholders(player, identifier));
                }
            }
            newMessages.add(message);
        }
        return newMessages;
    }

    public static String translatePlaceholders(UUID player, String identifier) {
        NumberFormat df = NumberFormat.getNumberInstance(Locale.getDefault());

        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        if(jp == null) return null;

        if(identifier.equals("job")) {
            if(!jp.hasJob()) {
                return LanguageManager.getFormat("none-format", player);
            }
            return jp.getJob().getDisplayName();
        }

        if(identifier.equals("tag")) {
            if(!jp.hasJob()) {
                return LanguageManager.getFormat("default-tag", player);
            }
            return jp.getJob().getTag();
        }

        if(identifier.equals("work")) {
            Job job = jp.getJob();
            if(job == null) {
                return LanguageManager.getFormat("none-format", player);
            }
            double level = jp.getMethodLevel();
            double req1 = level * job.getMethodFrequency();
            if(req1 <= 0) req1 = 1;
            int reqmethod = (int) (job.getMethod() * req1);
            if(reqmethod == 0) reqmethod = 1;
            double value = reqmethod - jp.getMethod();
            String workMessage = job.getWorkMessage();
            workMessage = workMessage.replace("%a%", df.format(value));
            return workMessage;
        }

        if(identifier.equals("reqmethod")) {
            Job job = jp.getJob();
            if(job == null) {
                return LanguageManager.getFormat("none-format", player);
            }
            double level = jp.getMethodLevel();
            double req1 = level * job.getMethodFrequency();
            if(req1 <= 0) req1 = 1;
            int reqmethod = (int) (job.getMethod() * req1);
            if(reqmethod == 0) reqmethod = 1;
            double value = reqmethod - jp.getMethod();
            return df.format(Math.round(value));
        }

        if(identifier.equals("salary")) {
            return df.format(Math.round(jp.getSalary()));
        }

        if(identifier.equals("level")) {
            return df.format(Math.round(jp.getMethodLevel()));
        }

        if(identifier.equals("working")) {
            return jp.isWorking() + "";
        }

        return "[NULL_PLACEHOLDER]";
    }
}
