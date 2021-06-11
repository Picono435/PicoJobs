package com.gmail.picono435.picojobs.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.commands.JobsCommand;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.TimeFormatter;

public class ClickInventoryListener implements Listener {
	
	public static Map<ItemStack, String> actionItems = new HashMap<ItemStack, String>();
	
	@EventHandler()
	public void onBasicClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.choose-job.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
			if(job == null) return;
			if(job.requiresPermission() && !p.hasPermission("picojobs.job." + job.getID())) {
				p.sendMessage(LanguageManager.getMessage("no-permission", p));
				return;
			}
			jp.setJob(job);
			p.sendMessage(LanguageManager.getMessage("choosed-job", p));
			p.closeInventory();
			return;
		}
		
		/*
		 * Accept Work Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.need-work.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			//Job job = jp.getJob();
			String action = actionItems.get(e.getCurrentItem());
			if(action == null) return;
			if(action.equalsIgnoreCase("salary")) {
				if(!jp.hasJob()) {
					p.sendMessage(LanguageManager.getMessage("no-args", p));
					return;
				}
				if(JobsCommand.salaryCooldown.containsKey(p.getUniqueId())) {
					long a1 = JobsCommand.salaryCooldown.get(p.getUniqueId()) + TimeUnit.MINUTES.toMillis(PicoJobsAPI.getSettingsManager().getSalaryCooldown());
					if(System.currentTimeMillis() >= a1) {
						JobsCommand.salaryCooldown.remove(p.getUniqueId());
					} else {
						p.sendMessage(LanguageManager.getMessage("salary-cooldown", p).replace("%cooldown_mtime%", TimeFormatter.formatTimeInMinecraft(a1 - System.currentTimeMillis()).replace("%cooldown_time%", TimeFormatter.formatTimeInRealLife(a1 - System.currentTimeMillis()))));
						p.closeInventory();
						return;
					}
				}
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					return;
				}
				String economyString = jp.getJob().getEconomy();
				if(!PicoJobsPlugin.getInstance().economies.containsKey(economyString)) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation said" + " (" + economyString + ")" + ". Please contact an administrator in order to get more information."));
					p.closeInventory();
					return;
				}
				EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(economyString);
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				economy.deposit(p, salary);
				jp.removeSalary(salary);
				JobsCommand.salaryCooldown.put(p.getUniqueId(), System.currentTimeMillis());
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("acceptwork")) {
				p.sendMessage(LanguageManager.getMessage("accepted-work", p));
				jp.setWorking(true);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("leavejob")) {
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				p.closeInventory();
				return;
			}
			return;
		}
		
		/*
		 * Status Work Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.has-work.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			//Job job = jp.getJob();
			String action = actionItems.get(e.getCurrentItem());
			if(action.equalsIgnoreCase("salary")) {
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					p.closeInventory();
					return;
				}
				String economyString = jp.getJob().getEconomy();
				if(!PicoJobsPlugin.getInstance().economies.containsKey(economyString)) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation said. Please contact an administrator for get more information."));
					p.closeInventory();
					return;
				}
				EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(economyString);
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				economy.deposit(p, salary);
				jp.removeSalary(salary);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("leavejob")) {
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				p.closeInventory();
				return;
			}
			return;
		}
	}
}
