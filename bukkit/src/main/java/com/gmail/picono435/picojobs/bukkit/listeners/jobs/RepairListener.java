package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class RepairListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCraftItem(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
		if(event.getInventory().getType() != InventoryType.ANVIL) return;
		if(event.getSlotType() != SlotType.RESULT) return;
		Player player = (Player) event.getWhoClicked();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.REPAIR)) return;
		if(!jp.isInWorkZone(player.getUniqueId())) return;
		
		if(!job.inWhitelist(Type.REPAIR, event.getCurrentItem().getType())) return;
		
		for(int i = 0; i < event.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				player.sendMessage(LanguageManager.getMessage("finished-work", player.getUniqueId()));
			}
		}
	}
}
