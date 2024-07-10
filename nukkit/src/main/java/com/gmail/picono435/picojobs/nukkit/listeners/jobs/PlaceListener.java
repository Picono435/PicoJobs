package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class PlaceListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.PLACE, event.getBlock());
	}
}
