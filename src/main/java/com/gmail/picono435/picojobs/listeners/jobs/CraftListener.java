package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class CraftListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCraftItem(CraftItemEvent e) {
		switch (e.getAction()) {
			case NOTHING:
			case PLACE_ONE:
			case PLACE_ALL:
			case PLACE_SOME:
				return;
			default:
				break;
		}
		if(e.getSlotType() != SlotType.RESULT) return;
		if (!e.isLeftClick() && !e.isRightClick()) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (p.getInventory().firstEmpty() == -1 && e.isShiftClick()) return;
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.CRAFT)) return;
		
		if(!job.inWhitelist(Type.CRAFT, e.getCurrentItem().getType())) return;
		
		for(int i = 0; i < e.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				p.sendMessage(LanguageManager.getMessage("finished-work", p));
			}
		}
	}
}
