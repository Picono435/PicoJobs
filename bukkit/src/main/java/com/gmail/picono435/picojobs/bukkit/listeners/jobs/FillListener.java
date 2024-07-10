package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class FillListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onFillBucket(PlayerBucketFillEvent  event) {
		Block block = event.getBlockClicked();
		if(!PicoJobsCommon.isLessThan("1.17.1")) {
			if(block == null || (!block.isLiquid())) return;
		} else {
			if(block == null || (!block.isLiquid() && block.getType().name().equals("POWDER_SNOW"))) return;
		}
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.FILL, block.getType());
	}
}
