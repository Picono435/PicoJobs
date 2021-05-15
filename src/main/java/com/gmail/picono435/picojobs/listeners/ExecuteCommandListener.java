package com.gmail.picono435.picojobs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class ExecuteCommandListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String cmd = e.getMessage().split(" ")[0];
		if(!PicoJobsAPI.getSettingsManager().getAllowedCommands().containsKey(cmd)) return;
		int level = PicoJobsAPI.getSettingsManager().getAllowedCommands().get(cmd);
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(jp.getMethodLevel() < level) {
			p.sendMessage(LanguageManager.getMessage("need-level", p).replace("%a%", level + ""));
			e.setCancelled(true);
			return;
		}
	}
}