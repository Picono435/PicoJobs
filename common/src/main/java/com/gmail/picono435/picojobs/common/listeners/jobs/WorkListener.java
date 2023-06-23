package com.gmail.picono435.picojobs.common.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Sender;

public class WorkListener {

    public static void simulateWorkListener(Sender player, Type type, int amount, Object object) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return;
        if(!jp.isWorking()) return;
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.KILL_ENTITY)) return;
        if(!jp.isInWorkZone(player.getUUID())) return;

        if(!job.inWhitelist(Type.KILL_ENTITY, object)) return;

        if(jp.simulateEvent()) {
            player.sendMessage(LanguageManager.getMessage("finished-work", player.getUUID()));
        }
    }
}
