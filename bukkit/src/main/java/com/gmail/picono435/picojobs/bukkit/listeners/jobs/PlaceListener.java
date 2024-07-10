package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.PLACE, event.getBlock().getType());
	}
}
