package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class RepairListener implements Listener {
	
	@EventHandler()
	public void onCraftItem(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if(e.getInventory().getType() != InventoryType.ANVIL) return;
		if(e.getSlotType() != SlotType.RESULT) return;
		if(e.getWhoClicked() == null) return;
		Player p = (Player) e.getWhoClicked();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.REPAIR) return;
		
		if(!job.inWhitelist(e.getCurrentItem().getType())) return;
		
		for(int i = 0; i < e.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				p.sendMessage(LanguageManager.getMessage("finished-work", p));
			}
		}
	}
}
