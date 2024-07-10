package com.gmail.picono435.picojobs.nukkit.listeners.jobs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityBlockChangeEvent;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class StripLogsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStripLogs(EntityBlockChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!event.getTo().getName().startsWith("STRIPPED_")) return;
        WorkListener.simulateWorkListener(new NukkitSender(player), Type.STRIP_LOGS, event.getTo());
    }
}
