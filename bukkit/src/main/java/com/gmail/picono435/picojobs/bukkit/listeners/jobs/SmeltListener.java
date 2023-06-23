package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class SmeltListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCraftItem(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
		if(!PicoJobsCommon.isMoreThan("1.14")) {
			if(event.getInventory().getType() != InventoryType.FURNACE && event.getInventory().getType() != InventoryType.valueOf("BLAST_FURNACE") && event.getInventory().getType() != InventoryType.valueOf("SMOKER")) return;
		} else {
			if(event.getInventory().getType() != InventoryType.FURNACE) return;
		}
		if(event.getSlotType() != SlotType.RESULT) return;
		Player player = (Player) event.getWhoClicked();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.SMELT, event.getCurrentItem().getAmount(), event.getCurrentItem().getType());
	}
}
