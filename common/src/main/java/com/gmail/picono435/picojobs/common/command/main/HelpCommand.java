package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.List;

public class HelpCommand implements Command {

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        sender.sendMessage(LanguageManager.getMessage("member-commands", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
