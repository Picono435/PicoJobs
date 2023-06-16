package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.utils.TimeFormatter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaveJobCommand implements Command {
    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
        if(!jp.hasJob()) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        if(jp.getLeaveCooldown() != 0) {
            long a1 = jp.getLeaveCooldown() + TimeUnit.MINUTES.toMillis(PicoJobsAPI.getSettingsManager().getLeaveCooldown());
            if(System.currentTimeMillis() >= a1) {
                jp.setLeaveCooldown(0);
            } else {
                sender.sendMessage(LanguageManager.getMessage("leave-cooldown", sender.getUUID())
                        .replace("%cooldown_mtime%", TimeFormatter.formatTimeInMinecraft(a1 - System.currentTimeMillis()))
                        .replace("%cooldown_time%", TimeFormatter.formatTimeInRealLife(a1 - System.currentTimeMillis())));
                return true;
            }
        }
        jp.removePlayerStats();
        sender.sendMessage(LanguageManager.getMessage("left-job", sender.getUUID()));
        jp.setLeaveCooldown(System.currentTimeMillis());
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
