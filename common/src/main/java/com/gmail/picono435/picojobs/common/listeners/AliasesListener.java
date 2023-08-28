package com.gmail.picono435.picojobs.common.listeners;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.Arrays;
import java.util.List;

public class AliasesListener {

    public static boolean onCommand(Sender sender, String message) {
        String cmd = message.split(" ")[0];
        if(!cmd.startsWith("/")) cmd = "/" + cmd;
        if(cmd.equals("/jobs")) return false;
        if(cmd.equals("/jobsadmin")) return false;
        List<String> args = Arrays.asList(message.split(" "));
        args.remove(0);
        if(LanguageManager.getCommandAliases("jobs").contains(cmd)) {
            sender.dispatchCommand("jobs " + String.join(" ", args));
            return true;
        }
        if(LanguageManager.getCommandAliases("jobsadmin").contains(cmd)) {
            sender.dispatchCommand("jobsadmin " + String.join(" ", args));
            return true;
        }
        return false;
    }
}
