package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.AliasesListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BukkitAliasesListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		event.setCancelled(AliasesListener.onCommand(new BukkitSender(event.getPlayer()), event.getMessage()));
	}
}