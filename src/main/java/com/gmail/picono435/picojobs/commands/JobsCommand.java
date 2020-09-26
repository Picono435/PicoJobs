package com.gmail.picono435.picojobs.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.JobsMenu;
import com.gmail.picono435.picojobs.utils.TimeFormatter;

public class JobsCommand implements CommandExecutor, TabCompleter {

	public static Map<UUID, Long> salaryCooldown = new HashMap<UUID, Long>();
	
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
				p.sendMessage(LanguageManager.getMessage("member-commands", p));
				return true;
			}
			String helpString = LanguageManager.getSubCommandAlias("help");
			String chooseString = LanguageManager.getSubCommandAlias("choose");
			String workString = LanguageManager.getSubCommandAlias("work");
			String salaryString = LanguageManager.getSubCommandAlias("salary");
			String withdrawString = LanguageManager.getSubCommandAlias("withdraw");
			String leaveString = LanguageManager.getSubCommandAlias("leave");
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase(helpString)) {
				p.sendMessage(LanguageManager.getMessage("member-commands", p));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("choose") || args[0].equalsIgnoreCase(chooseString)) {
				if(jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				Job jobTry = PicoJobsAPI.getJobsManager().getJob(args[1]);
				if(jobTry == null) {
					p.sendMessage(LanguageManager.getMessage("unknow-job", p));
					return true;
				}
				if(jobTry.requiresPermission() && !p.hasPermission("picojobs.job." + jobTry.getID())) {
					p.sendMessage(LanguageManager.getMessage("no-permission", p));
					return true;
				}
				jp.setJob(jobTry);
				p.sendMessage(LanguageManager.getMessage("choosed-job", p));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("work") || args[0].equalsIgnoreCase(workString)) {
				if(!jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				if(jp.isWorking()) {
					p.sendMessage(LanguageManager.getMessage("work-status", p));
					return true;
				}
				p.sendMessage(LanguageManager.getMessage("accepted-work", p));
				jp.setWorking(true);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("salary") || args[0].equalsIgnoreCase(salaryString)) {
				if(!jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				p.sendMessage(LanguageManager.getMessage("my-salary", p));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("withdraw") || args[0].equalsIgnoreCase(withdrawString)) {
				if(!jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				if(salaryCooldown.containsKey(p.getUniqueId())) {
					long a1 = salaryCooldown.get(p.getUniqueId()) + TimeUnit.MINUTES.toMillis(PicoJobsAPI.getSettingsManager().getSalaryCooldown());
					if(System.currentTimeMillis() >= a1) {
						salaryCooldown.remove(p.getUniqueId());
					} else {
						p.sendMessage(LanguageManager.getMessage("salary-cooldown", p).replace("%cooldown_mtime%", TimeFormatter.formatTimeInMinecraft(a1 - System.currentTimeMillis()).replace("%cooldown_time%", TimeFormatter.formatTimeInRealLife(a1 - System.currentTimeMillis()))));
						return true;
					}
				}
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					return true;
				}
				String economyString = jp.getJob().getEconomy();
				if(!PicoJobsPlugin.getInstance().economies.containsKey(economyString)) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation said" + " (" + economyString + ")" + ". Please contact an administrator in order to get more information."));
					return true;
				}
				EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(economyString);
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				economy.deposit(p, salary);
				jp.removeSalary(salary);
				salaryCooldown.put(p.getUniqueId(), System.currentTimeMillis());
				return true;
			}
			
			if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase(leaveString)) {
				if(!jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return true;
				}
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				return true;
			}
			
			if(args.length < 1) {
				p.sendMessage(LanguageManager.getMessage("member-commands", p));
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
			String help = LanguageManager.getSubCommandAlias("help");
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
				list.add(help);
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
						if(j.requiresPermission() && !p.hasPermission("picojobs.job." + j.getID())) continue;
						list.add(j.getID());
					}
					return list;
				}
			}
		}
		return null;
	}
}
