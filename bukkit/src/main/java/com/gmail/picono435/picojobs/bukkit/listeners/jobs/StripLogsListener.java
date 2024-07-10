package com.gmail.picono435.picojobs.bukkit.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class StripLogsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStripLogs(EntityChangeBlockEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!event.getTo().name().startsWith("STRIPPED_")) return;
        WorkListener.simulateWorkListener(new BukkitSender(player), Type.STRIP_LOGS, event.getBlock().getType());
    }
}
