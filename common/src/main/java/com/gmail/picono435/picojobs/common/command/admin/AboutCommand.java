package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.Arrays;
import java.util.List;

public class AboutCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("about", LanguageManager.getSubCommandAlias("about"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        String message = "&eHere are some information about the plugin\n"
                + "&ePlugin version:&6 v" + PicoJobsCommon.getVersion() + "\n"
                + "&e" + PicoJobsCommon.getPlatform().name() + " Version:&6 " + PicoJobsCommon.getPlatformAdapter().getPlatformVersion() + "\n"
                + "&eDiscord Server:&6 https://discord.gg/wQj53Hy\n"
                + "&eGitHub Repo:&6 https://github.com/Picono435/PicoJobs\n"
                + "&eIssues Tracker:&6 https://github.com/Picono435/PicoJobs/issues";
        sender.sendMessage(LanguageManager.formatMessage(message));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
