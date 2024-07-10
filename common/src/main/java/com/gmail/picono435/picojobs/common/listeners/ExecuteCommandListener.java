package com.gmail.picono435.picojobs.common.listeners;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Sender;

public class ExecuteCommandListener {

    public static boolean onCommand(Sender sender, String message) {
        String cmd = message.split(" ")[0];
        if(!PicoJobsAPI.getSettingsManager().getAllowedCommands().containsKey(cmd)) return false;
        int level = PicoJobsAPI.getSettingsManager().getAllowedCommands().get(cmd);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
        if(jp.getMethodLevel() < level) {
            sender.sendMessage(LanguageManager.getMessage("need-level", sender.getUUID()).replace("%a%", level + ""));
            return true;
        }
        return false;
    }
}
