package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.List;

public class ChooseCommand implements Command {
    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
        if(jp.hasJob()) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        Job job = PicoJobsAPI.getJobsManager().getJob(args[1]);
        if(job == null) {
            sender.sendMessage(LanguageManager.getMessage("unknow-job", sender.getUUID()));
            return true;
        }
        if(job.requirePermission() && !sender.hasPermission("picojobs.job." + job.getID())) {
            sender.sendMessage(LanguageManager.getMessage("no-permission", sender.getUUID()));
            return true;
        }
        jp.setJob(job);
        sender.sendMessage(LanguageManager.getMessage("choosed-job", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
