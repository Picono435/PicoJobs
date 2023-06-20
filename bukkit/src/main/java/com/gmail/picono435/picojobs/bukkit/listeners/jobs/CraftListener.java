package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
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
		if (player.getInventory().firstEmpty() == -1 && event.isShiftClick()) return;
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.CRAFT)) return;
		if(!jp.isInWorkZone(player.getUniqueId())) return;
		
		if(!job.inWhitelist(Type.CRAFT, event.getCurrentItem().getType())) return;
		
		for(int i = 0; i < event.getCurrentItem().getAmount(); i++) {
			if(jp.simulateEvent()) {
				player.sendMessage(LanguageManager.getMessage("finished-work", player.getUniqueId()));
			}
		}
	}
}
