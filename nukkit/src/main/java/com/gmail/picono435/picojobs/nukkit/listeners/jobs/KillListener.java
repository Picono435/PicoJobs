package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class KillListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() == null && !(event.getEntity().getKiller() instanceof Player)) return;
		Player player = (Player) event.getEntity().getKiller();
		JobPlayer jdead = PicoJobsAPI.getPlayersManager().getJobPlayer(event.getEntity().getUniqueId());
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.KILL, jdead.getJob());
	}
}
