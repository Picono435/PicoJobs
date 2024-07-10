package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() == null) return;
		Player player = event.getEntity().getKiller();
		JobPlayer jdead = PicoJobsAPI.getPlayersManager().getJobPlayer(event.getEntity().getUniqueId());
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.KILL, jdead.getJob());
	}
}
