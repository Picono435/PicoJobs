package com.gmail.picono435.picojobs.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class ClickInventoryListener implements Listener {
	
	public static Map<ItemStack, String> actionItems = new HashMap<ItemStack, String>();
	
	@EventHandler()
	public void onChooseJob(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.choose-job.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
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
			if(action.equalsIgnoreCase("salary")) {
				double salary = jp.getSalary();
				if(salary >= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					return;
				}
				if(!VaultHook.isEnabled() || !VaultHook.hasEconomyPlugin()) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find any compatible economy plugin in the server. Please contact an adminstrator, as this option does not work without it."));
					return;
				}
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				jp.removeSalary(salary);
				return;
			}
			if(action.equalsIgnoreCase("acceptwork")) {
				p.sendMessage(LanguageManager.getMessage("accepted-work", p));
				jp.setWorking(true);
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
				if(salary >= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					return;
				}
				if(!VaultHook.isEnabled() || !VaultHook.hasEconomyPlugin()) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find any compatible economy plugin in the server. Please contact an adminstrator, as this option does not work without it."));
					return;
				}
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				jp.removeSalary(salary);
				return;
			}
			return;
		}
	}
}
