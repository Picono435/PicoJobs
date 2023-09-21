package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.events.PlayerEmployedEvent;
import com.gmail.picono435.picojobs.api.events.PlayerWithdrawEvent;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("choose", LanguageManager.getSubCommandAlias("choose"));
    }

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
        PlayerEmployedEvent event = PicoJobsAPI.getEventsManager().consumeListeners(new PlayerEmployedEvent(jp, job));
        if (event.isCancelled()) {
            return true;
        }
        jp.setJob(event.getJob());
        sender.sendMessage(LanguageManager.getMessage("choosed-job", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        List<String> tabCompletion = new ArrayList<>();
        if(args.length == 2) {
            tabCompletion.addAll(PicoJobsCommon.getMainInstance().jobs.keySet());
        }
        return tabCompletion;
    }
}
