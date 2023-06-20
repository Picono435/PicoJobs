package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class MilkListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTakeMilk(PlayerBucketFillEvent  event) {
		Block b = event.getBlockClicked();
		if(b.isLiquid()) return;
		Player player = event.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.MILK)) return;
		if(!jp.isInWorkZone(player.getUniqueId())) return;
		if(jp.simulateEvent()) {
			player.sendMessage(LanguageManager.getMessage("finished-work", player.getUniqueId()));
		}
	}
}
