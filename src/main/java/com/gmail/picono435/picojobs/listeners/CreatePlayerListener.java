package com.gmail.picono435.picojobs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.JobPlayer;

public class CreatePlayerListener implements Listener {
	
	@EventHandler()
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!PicoJobsPlugin.playersdata.containsKey(p.getUniqueId())) {
			PicoJobsPlugin.playersdata.put(p.getUniqueId(), new JobPlayer(null, 0, 1, 0, false));
		}
	}
}
