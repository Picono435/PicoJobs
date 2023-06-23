package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class TradeListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTrade(InventoryClickEvent event) {
		if(event.getInventory().getType() != InventoryType.MERCHANT) return;
		if(event.getSlotType() != InventoryType.SlotType.RESULT) return;
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
		Player player = (Player) event.getWhoClicked();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.TRADE, event.getCurrentItem().getAmount(), event.getCurrentItem().getType());
	}
}
