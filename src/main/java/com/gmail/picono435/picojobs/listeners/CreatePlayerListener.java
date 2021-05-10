package com.gmail.picono435.picojobs.listeners;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.managers.LanguageManager;

import mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.ChatColor;

public class CreatePlayerListener implements Listener {
	
	@EventHandler()
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		try {
			if(!PicoJobsAPI.getStorageManager().getStorageFactory().playerExists(p.getUniqueId())) {
				PicoJobsAPI.getStorageManager().getStorageFactory().createPlayer(p.getUniqueId());
			}
		} catch (Exception ex) {
			p.kickPlayer("§6§lPicoJobs §c§l| Error Message\n\n§cThere was an issue contacting to the database. Please contact a administrator too check the console for errors.");
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, "Error connecting to the storage. The plugin will not work correctly.");
			ex.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCheckVersionJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		// VERSION CHECKER
		new BukkitRunnable() {
			public void run() {
				if(p.hasPermission("picojobs.admin") && PicoJobsPlugin.getInstance().isOldVersion()) {
					String message = "\n" + LanguageManager.formatMessage("&cYou are using an old version of PicoJobs. This new version can include fixes to current errors.\n&7 You can update automatically by clicking in this message.\n&c");
					new FancyMessage(message)
							.command("/jobsadmin update")
							.tooltip(ChatColor.RED + "Click here to update PicoJobs plugin to v" + PicoJobsPlugin.getInstance().getLastestPluginVersion())
							.send(p);
				}
			}
		}.runTaskLater(PicoJobsPlugin.getInstance(), 20L);
	}
}
