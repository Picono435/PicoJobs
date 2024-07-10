package com.gmail.picono435.picojobs.common.inventory;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;

import java.util.UUID;

public class JobsMenu {

    public static String getMenu(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        if(jp.hasJob()) {
            if(jp.isWorking()) {
                return "has-work";
            } else {
                return "need-work";
            }
        } else {
            return "choose-job";
        }
    }
}
