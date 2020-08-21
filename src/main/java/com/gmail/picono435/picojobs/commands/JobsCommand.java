package com.gmail.picono435.picojobs.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.JobsMenu;

public class JobsCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobs")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage(LanguageManager.formatMessage("&cOnly players can use that command, please use /jobsadmin to see the help of JobsAdmin commands."));
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
		
		int action = PicoJobsAPI.getSettingsManager().getCommandAction();
		
		if(action == 1) {
			p.sendMessage(LanguageManager.getMessage("ignore-action", p));
			return true;
		} else if(action == 2) {
			if(args.length < 1) {
				p.sendMessage(LanguageManager.getMessage("no-args", p));
				return true;
			}
			String chooseString = LanguageManager.getSubCommandAlias("choose");
			String workString = LanguageManager.getSubCommandAlias("work");
			String salaryString = LanguageManager.getSubCommandAlias("salary");
			String withdrawString = LanguageManager.getSubCommandAlias("withdraw");
			String leaveString = LanguageManager.getSubCommandAlias("leave");
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			
			if(args[0].equalsIgnoreCase("choose") || args[0].equalsIgnoreCase(chooseString)) {
				if(args.length < 2) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				Job jobTry = PicoJobsAPI.getJobsManager().getJob(args[1]);
				if(jobTry == null) {
					p.sendMessage(LanguageManager.getMessage("unknow-job", p));
					return true;
				}
				if(jobTry.requiresPermission() && !p.hasPermission("picojobs.job." + jobTry.getName())) {
					p.sendMessage(LanguageManager.getMessage("no-permission", p));
					return true;
				}
				jp.setJob(jobTry);
				p.sendMessage(LanguageManager.getMessage("choosed-job", p));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("work") || args[0].equalsIgnoreCase(workString)) {
				if(jp.isWorking()) {
					p.sendMessage(LanguageManager.getMessage("work-status", p));
					return true;
				}
				p.sendMessage(LanguageManager.getMessage("accepted-work", p));
				jp.setWorking(true);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("salary") || args[0].equalsIgnoreCase(salaryString)) {
				p.sendMessage(LanguageManager.getMessage("my-salary", p));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("withdraw") || args[0].equalsIgnoreCase(withdrawString)) {
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					p.closeInventory();
					return true;
				}
				if(!VaultHook.isEnabled() || !VaultHook.hasEconomyPlugin()) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find any compatible economy plugin in the server. Please contact an adminstrator, as this option does not work without it."));
					p.closeInventory();
					return true;
				}
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				VaultHook.getEconomy().depositPlayer(p, salary);
				jp.removeSalary(salary);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase(leaveString)) {
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				return true;
			}
			
			return true;
		} else {
			JobsMenu.openMenu(p);
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("jobs")) {
			String choose = LanguageManager.getSubCommandAlias("choose");
			String work = LanguageManager.getSubCommandAlias("work");
			String salary = LanguageManager.getSubCommandAlias("salary");
			String withdraw = LanguageManager.getSubCommandAlias("withdraw");
			String leave = LanguageManager.getSubCommandAlias("leave");
			
			int action = PicoJobsAPI.getSettingsManager().getCommandAction();
			if(action != 2) return null;
			if(args.length == 1) {
				JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
				List<String> list = new ArrayList<String>();
				if(!jp.hasJob()) {
					list.add(choose);
				} else {
					list.add(work);
					list.add(salary);
					list.add(withdraw);
					list.add(leave);
				}
				return list;
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("choose") || args[0].equalsIgnoreCase(choose)) {
					List<String> list = new ArrayList<String>();
					for(Job j : PicoJobsAPI.getJobsManager().getJobs()) {
						if(j.requiresPermission() && !p.hasPermission("picojobs.job." + j.getName())) continue;
						list.add(j.getName());
					}
					return list;
				}
			}
		}
		return null;
	}
}
