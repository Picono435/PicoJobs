package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class KillEntityListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
		if(!(event.getEntity().getLastDamageCause().getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity().getLastDamageCause().getEntity();
		WorkListener.simulateWorkListener(new NukkitSender(player), Type.KILL_ENTITY, event.getEntity());
	}
}
