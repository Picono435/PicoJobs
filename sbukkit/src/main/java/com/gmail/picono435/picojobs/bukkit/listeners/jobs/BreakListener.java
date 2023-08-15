package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.CropState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;
import org.bukkit.metadata.FixedMetadataValue;

public class BreakListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		boolean isNaturalBlock = event.getBlock().getMetadata("PLACED").size() == 0;
		if(PicoJobsCommon.isLessThan("1.13.2")) {
			if(event.getBlock().getState().getData() instanceof Crops) {
				if (((Crops) event.getBlock().getState().getData()).getState() != CropState.RIPE) return;
				isNaturalBlock = true;
			}
		} else {
			if(event.getBlock().getBlockData() instanceof Ageable) {
				Ageable ageable = (Ageable) event.getBlock().getBlockData();
				if(ageable.getMaximumAge() != ageable.getAge()) return;
				isNaturalBlock = true;
			}
		}
		if(!isNaturalBlock) return;

		WorkListener.simulateWorkListener(new BukkitSender(player), Type.BREAK, event.getBlock().getType());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent event) {
		event.getBlock().setMetadata("PLACED", new FixedMetadataValue(PicoJobsBukkit.getInstance(), "YES"));
	}
}