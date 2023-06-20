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
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
		if(jp == null) return;
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.MOVE)) return;
		if(!jp.isInWorkZone(player.getUniqueId())) return;

		if(!job.inWhitelist(Type.MOVE, player.getLocation().getBlock().getType())) return;

		long distance = Math.round(Math.floor(event.getTo().distance(event.getFrom())));

		for(int i = 0; i < distance; i++) {
			if(jp.simulateEvent()) {
				player.sendMessage(LanguageManager.getMessage("finished-work", player.getUniqueId()));
				return;
			}
		}
	}
}
