package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.block.BlockCrops;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.metadata.MetadataValue;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class BreakListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreakBlock(BlockBreakEvent event) throws Exception {
		Player player = event.getPlayer();
		boolean isNaturalBlock = event.getBlock().getMetadata("PLACED").size() == 0;
		if(event.getBlock() instanceof BlockCrops) {
			if(event.getBlock().getDamage() == 7) isNaturalBlock = true;
		}
		if(!isNaturalBlock) return;

		WorkListener.simulateWorkListener(new NukkitSender(player), Type.BREAK, event.getBlock());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent event) throws Exception {
		event.getBlock().setMetadata("PLACED", new MetadataValue(PicoJobsNukkit.getInstance()) {
			@Override
			public Object value() {
				return "YES";
			}

			@Override
			public void invalidate() {

			}
		});
	}
}
