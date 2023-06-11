package com.gmail.picono435.picojobs.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class StripLogsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStripLogs(EntityChangeBlockEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player p = (Player) event.getEntity();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
        if(!jp.hasJob()) return;
        if(!jp.isWorking()) return;
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.STRIP_LOGS)) return;
        if(!event.getTo().name().startsWith("STRIPPED_")) return;
        if(!jp.isInWorkZone(p)) return;

        if(!job.inWhitelist(Type.STRIP_LOGS, event.getBlock().getType())) return;

        if(jp.simulateEvent()) {
            p.sendMessage(LanguageManager.getMessage("finished-work", p));
        }
    }
}
