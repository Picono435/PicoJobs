package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBucketFillEvent;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class FillListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onFillBucket(PlayerBucketFillEvent event) {
		Block block = event.getBlockClicked();
		if(block == null) return;
		if((!(block instanceof BlockLiquid))) return;
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.FILL, block);
	}
}
