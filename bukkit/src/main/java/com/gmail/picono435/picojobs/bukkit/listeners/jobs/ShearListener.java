package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onShearEntity(PlayerShearEntityEvent event) {
		if(!(event.getEntity() instanceof Sheep)) return;
		Player player = event.getPlayer();
		WorkListener.simulateWorkListener(new BukkitSender(player), Type.SHEAR, ((Sheep) event.getEntity()).getColor());
	}

}
