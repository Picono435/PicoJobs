package com.gmail.picono435.picojobs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.picono435.picojobs.managers.LanguageManager;

public class JobsAdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobsadmin")) return false;
		CommandSender p = sender;
		if(!p.hasPermission("picojobs.admin")) {
			p.sendMessage(LanguageManager.getMessage("no-permission"));
			return true;
		}
		p.sendMessage(LanguageManager.formatMessage("&cThis feature is not added yet. ;("));
		return false;
	}

}
