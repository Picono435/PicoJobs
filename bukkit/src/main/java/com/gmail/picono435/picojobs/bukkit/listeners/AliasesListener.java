package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AliasesListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String cmd = event.getMessage().split(" ")[0];
		if(!cmd.startsWith("/")) cmd = "/" + cmd;
		if(cmd.equals("/jobs")) return;
		if(cmd.equals("/jobsadmin")) return;
		if(LanguageManager.getCommandAliases("jobs").contains(cmd)) {
			Bukkit.dispatchCommand(player, "jobs");
			event.setCancelled(true);
			return;
		}
		if(LanguageManager.getCommandAliases("jobsadmin").contains(cmd)) {
			Bukkit.dispatchCommand(player, "jobsadmin");
			event.setCancelled(true);
			return;
		}
	}
}