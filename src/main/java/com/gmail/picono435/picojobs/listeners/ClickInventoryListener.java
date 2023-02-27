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
		if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.choose-job.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
			if(job == null) return;
			if(job.requirePermission() && !p.hasPermission("picojobs.job." + job.getID())) {
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
				JobsCommand.runWithdraw(p, jp);
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
				JobsCommand.runLeaveJob(p, jp);
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
			if(action == null) return;
			if(action.equalsIgnoreCase("salary")) {
				JobsCommand.runWithdraw(p, jp);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("leavejob")) {
				JobsCommand.runLeaveJob(p, jp);
				p.closeInventory();
				return;
			}
			return;
		}
	}
}
