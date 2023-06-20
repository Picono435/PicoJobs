package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ExecuteCommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().split(" ")[0];
        if(!PicoJobsAPI.getSettingsManager().getAllowedCommands().containsKey(cmd)) return;
        int level = PicoJobsAPI.getSettingsManager().getAllowedCommands().get(cmd);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
        if(jp.getMethodLevel() < level) {
            player.sendMessage(LanguageManager.getMessage("need-level", player.getUniqueId()).replace("%a%", level + ""));
            event.setCancelled(true);
            return;
        }
    }
}
