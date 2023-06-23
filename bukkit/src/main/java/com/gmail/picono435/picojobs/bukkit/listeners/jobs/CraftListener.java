package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class CraftListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCraftItem(CraftItemEvent event) {
		switch (event.getAction()) {
			case NOTHING:
			case PLACE_ONE:
			case PLACE_ALL:
			case PLACE_SOME:
				return;
			default:
				break;
		}
		if(event.getSlotType() != SlotType.RESULT) return;
		if (!event.isLeftClick() && !event.isRightClick()) return;
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.CRAFT, event.getCurrentItem().getAmount(), event.getCurrentItem().getType());
	}
}
