package com.gmail.picono435.picojobs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class CreatePlayerListener implements Listener {
	
	@EventHandler()
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!PicoJobsPlugin.playersdata.containsKey(p.getUniqueId())) {
			PicoJobsPlugin.playersdata.put(p.getUniqueId(), new JobPlayer(null, 0, 1, 0, false));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCheckVersionJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		new BukkitRunnable() {
			public void run() {
				if(p.hasPermission("picojobs.admin") && PicoJobsPlugin.isOldVersion()) {
					p.sendMessage("\n" + LanguageManager.formatMessage("&cYou are using an old version of PicoJobs, please update. This new version can include fixes to current errors.\n&c"));
				}
			}
		}.runTaskLater(PicoJobsPlugin.getPlugin(), 20L);
	}
}
