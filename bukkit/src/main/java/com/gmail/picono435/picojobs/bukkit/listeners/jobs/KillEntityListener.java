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
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		System.out.println("1");
		if(event.getEntity().getKiller() == null) return;
		System.out.println("2");
		Player player = event.getEntity().getKiller();
		System.out.println("3");
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
		if(!jp.hasJob()) return;
		System.out.println("4");
		if(!jp.isWorking()) return;
		System.out.println("5");
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.KILL_ENTITY)) return;
		System.out.println("6");
		if(!jp.isInWorkZone(player.getUniqueId())) return;
		System.out.println("7");
		
		if(!job.inWhitelist(Type.KILL_ENTITY, event.getEntity().getType())) return;
		System.out.println("8");
		
		if(jp.simulateEvent()) {
			player.sendMessage(LanguageManager.getMessage("finished-work", player.getUniqueId()));
		}
	}
}
