package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class KillEntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() == null) return;
		Player p = e.getEntity().getKiller();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.KILL_ENTITY)) return;
		
		if(!job.inWhitelist(Type.KILL_ENTITY, e.getEntity().getType())) return;
		
		if(jp.simulateEvent()) {
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
}
