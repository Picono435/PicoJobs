package com.gmail.picono435.picojobs.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SwimListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onSwim(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.getLocation().getBlock().getType() != Material.WATER && p.getLocation().getBlock().getType() != Material.LAVA) return;
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.SWIM)) return;

		long distance = Math.round(Math.floor(e.getTo().distance(e.getFrom())));

		for(int i = 0; i < distance; i++) {
			if(jp.simulateEvent()) {
				p.sendMessage(LanguageManager.getMessage("finished-work", p));
				return;
			}
		}
	}
}
