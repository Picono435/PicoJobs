package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class SmeltListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCraftItem(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if(!PicoJobsPlugin.getInstance().isMoreThan("1.14")) {
			if(e.getInventory().getType() != InventoryType.FURNACE && e.getInventory().getType() != InventoryType.valueOf("BLAST_FURNACE") && e.getInventory().getType() != InventoryType.valueOf("SMOKER")) return;
		} else {
			if(e.getInventory().getType() != InventoryType.FURNACE) return;
		}
		if(e.getSlotType() != SlotType.RESULT) return;
		Player p = (Player) e.getWhoClicked();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.SMELT)) return;
		if(!jp.isInWorkZone(p)) return;
		
		if(!job.inWhitelist(Type.SMELT, e.getCurrentItem().getType())) return;
		
		for(int i = 0; i < e.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				p.sendMessage(LanguageManager.getMessage("finished-work", p));
			}
		}
	}
}
