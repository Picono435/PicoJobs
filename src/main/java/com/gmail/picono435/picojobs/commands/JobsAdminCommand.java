package com.gmail.picono435.picojobs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class JobsAdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobsadmin")) return false;
		CommandSender p = sender;
		if(!p.hasPermission("picojobs.admin")) {
			p.sendMessage(LanguageManager.formatMessage("&7PicoJobs v" + PicoJobsPlugin.getInstance().getDescription().getVersion() + ". (&8&nhttps://discord.gg/wQj53Hy&r&7)"));
			return true;
		}
		p.sendMessage(LanguageManager.formatMessage("&7PicoJobs v" + PicoJobsPlugin.getInstance().getDescription().getVersion() + ". (&8&nhttps://discord.gg/wQj53Hy&r&7)"));
		return false;
	}

}
