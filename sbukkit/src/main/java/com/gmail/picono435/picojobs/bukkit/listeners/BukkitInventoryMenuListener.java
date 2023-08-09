package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.bukkit.platform.BukkitInventoryAdapter;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BukkitInventoryMenuListener implements Listener {

	@EventHandler()
	public void onBasicClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
		event.setCancelled(InventoryMenuListener.onBasicClick(new BukkitSender(event.getWhoClicked()),
				new BukkitInventoryAdapter(event.getInventory(), event.getView().getTitle()),
				event.getSlot(),
				event.getCurrentItem()));
	}
}
