package com.gmail.picono435.picojobs.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class JobsAdminCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobsadmin")) return false;
		CommandSender p = sender;
		if(!p.hasPermission("picojobs.admin")) {
			p.sendMessage(LanguageManager.formatMessage("&7PicoJobs v" + PicoJobsPlugin.getInstance().getDescription().getVersion() + ". (&8&nhttps://discord.gg/wQj53Hy&r&7)"));
			return true;
		}
		if(args.length < 1) {
			p.sendMessage(LanguageManager.getFormat("admin-commands", null));
			return true;
		}
		String subcmd = args[0];
		String helpString = LanguageManager.getSubCommandAlias("help");
		String infoString = LanguageManager.getSubCommandAlias("info");
		String reloadString = LanguageManager.getSubCommandAlias("reload");
		Player pl = null;
		if(sender instanceof Player) {
			pl = (Player)sender;
		}
		
		// HELP COMMAND
		if(subcmd.equalsIgnoreCase("help") || subcmd.equalsIgnoreCase(helpString)) {
			p.sendMessage(LanguageManager.getFormat("admin-commands", pl));
			return true;
		}
		
		// INFO COMMAND
		if(subcmd.equalsIgnoreCase("info") || subcmd.equalsIgnoreCase(infoString)) {
			if(args.length < 2) {
				p.sendMessage(LanguageManager.getMessage("no-args", pl));
				return true;
			}
			String playername = args[1];
			Player player = Bukkit.getPlayer(playername);
			if(player == null) {
				p.sendMessage(LanguageManager.getMessage("player-not-found", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getFormat("info-command", player));
			return true;
		}
		
		// RELOAD COMMAND
		if(subcmd.equalsIgnoreCase("reload") || subcmd.equalsIgnoreCase(reloadString)) {
			if(!FileCreator.reloadFiles()) {
				p.sendMessage(LanguageManager.getMessage("unknow-error", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getMessage("reload-command", pl));
			return true;
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("jobsadmin")) {
			String helpString = LanguageManager.getSubCommandAlias("help");
			String infoString = LanguageManager.getSubCommandAlias("info");
			String reloadString = LanguageManager.getSubCommandAlias("reload");
			
			int action = PicoJobsAPI.getSettingsManager().getCommandAction();
			if(action != 2) return null;
			if(args.length == 1) {
				List<String> list = new ArrayList<String>();
				if(p.hasPermission("picojobs.admin")) {
					list.add(helpString);
					list.add(infoString);
					list.add(reloadString);
				}
				return list;
			}
		}
		return null;
	}

}
