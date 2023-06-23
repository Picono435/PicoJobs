package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
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
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.REPAIR, event.getCurrentItem().getAmount(), event.getCurrentItem().getType());
	}
}
