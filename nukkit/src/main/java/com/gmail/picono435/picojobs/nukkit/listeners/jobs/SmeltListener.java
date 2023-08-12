package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.inventory.InventoryType;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class SmeltListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onSmelt(InventoryClickEvent event) {
		if(event.getHeldItem() == null) return;
		if(event.getInventory().getType() != InventoryType.FURNACE) return;
		if(event.getSlot() != 2) return;
		Player player = (Player) event.getPlayer();
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.SMELT, event.getHeldItem().count, event.getHeldItem());
	}
}
