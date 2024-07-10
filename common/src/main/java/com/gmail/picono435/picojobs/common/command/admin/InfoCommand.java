package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InfoCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("info", LanguageManager.getSubCommandAlias("info"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        if(args.length < 2) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        String playername = args[1];
        Optional<UUID> player = PicoJobsCommon.getPlatformAdapter().getPlayerUUID(playername);
        if(!player.isPresent()) {
            sender.sendMessage(LanguageManager.getMessage("player-not-found", sender.getUUID()));
            return true;
        }
        sender.sendMessage(LanguageManager.getFormat("info-command", player.get()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
