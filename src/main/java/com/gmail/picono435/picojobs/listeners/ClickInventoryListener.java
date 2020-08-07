package com.gmail.picono435.picojobs.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.vars.Job;
import com.gmail.picono435.picojobs.vars.JobPlayer;

public class ClickInventoryListener implements Listener {
	
	public static Map<ItemStack, String> actionItems = new HashMap<ItemStack, String>();
	
	@EventHandler()
	public void onChooseJob(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(PicoJobsPlugin.getPlugin().getConfig().getString("gui-settings.choose-job.title"))) {
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
		if(e.getView().getTitle().equals(PicoJobsPlugin.getPlugin().getConfig().getString("gui-settings.need-work.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			Job job = jp.getJob();
			String action = actionItems.get(e.getCurrentItem());
			if(action.equalsIgnoreCase("salary")) {
				double salary = jp.getSalary();
				p.sendMessage("Recebeste " + salary + "o teu salário com sucesso.");
				return;
			}
			if(action.equalsIgnoreCase("acceptwork")) {
				p.sendMessage("Aceitaste o teu trabalho com sucesso.");
				jp.setWorking(true);
				return;
			}
			return;
		}
	}
}
