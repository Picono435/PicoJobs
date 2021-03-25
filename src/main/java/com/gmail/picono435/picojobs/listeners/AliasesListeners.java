package com.gmail.picono435.picojobs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.picono435.picojobs.managers.LanguageManager;

public class AliasesListeners implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String cmd = e.getMessage().split(" ")[0];
		if(!cmd.startsWith("/")) cmd = "/" + cmd;
		if(cmd.equals("/jobs")) return;
		if(cmd.equals("/jobsadmin")) return;
		if(LanguageManager.getCommandAliases("jobs").contains(cmd)) {
			Bukkit.dispatchCommand(p, "jobs");
			e.setCancelled(true);
			return;
		}
		if(LanguageManager.getCommandAliases("jobsadmin").contains(cmd)) {
			Bukkit.dispatchCommand(p, "jobsadmin");
			e.setCancelled(true);
			return;
		}
	}
}