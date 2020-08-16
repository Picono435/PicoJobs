package com.gmail.picono435.picojobs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.JobsMenu;

import net.md_5.bungee.api.ChatColor;

public class JobsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobs")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use that command, please use /jobsadmin to see the help of JobsAdmin commands.");
			return false;
		}
		Player p = (Player)sender;
		// 1 = Ignore, recommended to use Citizens
		// 2 = Execute basic commands, enter a job, get salary etc... etc...
		// 3 = Open Jobs Menu
		if(!p.hasPermission("picojobs.use.basic")) {
			p.sendMessage(LanguageManager.getMessage("no-permission"));
			return true;
		}
		
		int action = PicoJobsPlugin.getPlugin().getConfig().getInt("jobs-action");
		
		if(action == 1) {
			p.sendMessage(LanguageManager.getMessage("ignore-action", p));
			return true;
		} else if(action == 2) {
			if(args.length < 1) {
				p.sendMessage(LanguageManager.formatMessage("&cThis feature is not added yet. ;("));
				return true;
			}
			return true;
		} else {
			JobsMenu.openMenu(p);
			return true;
		}
	}

}
