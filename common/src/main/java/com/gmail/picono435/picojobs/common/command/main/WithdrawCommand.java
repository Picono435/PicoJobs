package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.utils.TimeFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WithdrawCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("withdraw", LanguageManager.getSubCommandAlias("withdraw"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
        if(!jp.hasJob()) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        if(jp.getSalaryCooldown() != 0) {
            long a1 = jp.getSalaryCooldown() + TimeUnit.MINUTES.toMillis(PicoJobsAPI.getSettingsManager().getSalaryCooldown());
            if(System.currentTimeMillis() >= a1) {
                jp.setSalaryCooldown(0);
            } else {
                sender.sendMessage(LanguageManager.getMessage("salary-cooldown", sender.getUUID())
                        .replace("%cooldown_mtime%", TimeFormatter.formatTimeInMinecraft(a1 - System.currentTimeMillis()))
                        .replace("%cooldown_time%", TimeFormatter.formatTimeInRealLife(a1 - System.currentTimeMillis())));
                return true;
            }
        }
        double salary = jp.getSalary();
        if(salary <= 0) {
            sender.sendMessage(LanguageManager.getMessage("no-salary", sender.getUUID()));
            return true;
        }
        String economyString = jp.getJob().getEconomy();
        if(!PicoJobsCommon.getMainInstance().economies.containsKey(economyString)) {
            sender.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation (" + economyString + "). Please contact an administrator in order to get more information."));
            return true;
        }
        EconomyImplementation economy = PicoJobsCommon.getMainInstance().economies.get(economyString);
        sender.sendMessage(LanguageManager.getMessage("got-salary", sender.getUUID()));
        economy.deposit(sender.getUUID(), salary);
        jp.removeSalary(salary);
        jp.setSalaryCooldown(System.currentTimeMillis());
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
