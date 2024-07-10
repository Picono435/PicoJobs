package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.Arrays;
import java.util.List;

//TODO: Create the update command and system
public class UpdateCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("update", LanguageManager.getSubCommandAlias("update"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        return false;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
