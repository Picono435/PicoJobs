package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBucketFillEvent;
import cn.nukkit.item.food.FoodMilk;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class FillEntityListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTakeMilk(PlayerBucketFillEvent event) {
		if(event.getBucket().getDamage() != 1) return;
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.FILL_ENTITY, null);
	}
}
