package com.gmail.picono435.picojobs.listeners.jobs;

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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class TradeListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTrade(InventoryClickEvent e) {
		if(e.getInventory().getType() != InventoryType.MERCHANT) return;
		if(e.getSlotType() != InventoryType.SlotType.RESULT) return;
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		Player p = (Player) e.getWhoClicked();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.TRADE)) return;

		if(!job.inWhitelist(e.getCurrentItem().getType())) return;

		for(int i = 0; i < e.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				p.sendMessage(LanguageManager.getMessage("finished-work", p));
				return;
			}
		}
	}
}
