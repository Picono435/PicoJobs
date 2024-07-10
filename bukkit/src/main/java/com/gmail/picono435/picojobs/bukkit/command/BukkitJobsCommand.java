package com.gmail.picono435.picojobs.bukkit.command;

import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class BukkitJobsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return PicoJobsCommon.getMainInstance().getJobsCommand().onCommand(command.getName(), args, new BukkitSender(sender));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return PicoJobsCommon.getMainInstance().getJobsCommand().getTabCompletions(command.getName(), args, new BukkitSender(sender));
    }
}
