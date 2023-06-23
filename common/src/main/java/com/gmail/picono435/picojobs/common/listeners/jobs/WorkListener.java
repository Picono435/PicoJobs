package com.gmail.picono435.picojobs.common.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Sender;

public class WorkListener {

    public static void simulateWorkListener(Sender player, Type type, Object object) {
        simulateWorkListener(player, type, 1, object);
    }

    public static void simulateWorkListener(Sender player, Type type, int amount, Object object) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return;
        if(!jp.isWorking()) return;
        Job job = jp.getJob();
        if(!job.getTypes().contains(type)) return;
        if(!jp.isInWorkZone(player.getUUID())) return;

        if(!job.inWhitelist(type, object)) return;

        for(int i = 0; i < amount; i++) {
            if(jp.simulateEvent()) {
                player.sendMessage(LanguageManager.getMessage("finished-work", player.getUUID()));
                if(!jp.isWorking()) break;
            }
        }
    }
}
