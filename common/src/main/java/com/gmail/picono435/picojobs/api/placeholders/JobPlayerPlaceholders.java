package com.gmail.picono435.picojobs.api.placeholders;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.api.managers.PlaceholderManager;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * This class will translate only PICOJOBS placeholders and not any placeholder
 */
public class JobPlayerPlaceholders extends PlaceholderExtension {

    public final static String PREFIX = "jobplayer";
    public final static String[] PLACEHOLDERS = {"job", "tag", "work", "reqmethod", "salary", "level", "working"};

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String[] getPlaceholders() {
        return PLACEHOLDERS;
    }

    public String translatePlaceholders(UUID player, String identifier) {
        NumberFormat df = NumberFormat.getNumberInstance(Locale.getDefault());

        if(player == null) return null;
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
            return String.valueOf(jp.isWorking());
        }

        return PlaceholderManager.NULL_PLACEHOLDER;
    }
}
