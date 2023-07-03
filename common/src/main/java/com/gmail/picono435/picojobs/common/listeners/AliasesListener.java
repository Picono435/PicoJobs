package com.gmail.picono435.picojobs.common.listeners;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Sender;

public class AliasesListener {

    //TODO: This is wrong lol? No arguments are being allowed
    public static boolean onCommand(Sender sender, String message) {
        String cmd = message.split(" ")[0];
        if(!cmd.startsWith("/")) cmd = "/" + cmd;
        if(cmd.equals("/jobs")) return false;
        if(cmd.equals("/jobsadmin")) return false;
        if(LanguageManager.getCommandAliases("jobs").contains(cmd)) {
            sender.dispatchCommand("jobs");
            return true;
        }
        if(LanguageManager.getCommandAliases("jobsadmin").contains(cmd)) {
            sender.dispatchCommand("jobsadmin");
            return true;
        }
        return false;
    }
}
