package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.List;

public class WorkCommand implements Command {
    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
        if(!jp.hasJob()) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        if(jp.isWorking()) {
            sender.sendMessage(LanguageManager.getMessage("work-status", sender.getUUID()));
            return true;
        }
        sender.sendMessage(LanguageManager.getMessage("accepted-work", sender.getUUID()));
        jp.setWorking(true);
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
