package com.gmail.picono435.picojobs.nukkit.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import com.gmail.picono435.picojobs.common.listeners.AliasesListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class NukkitAliasesListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(AliasesListener.onCommand(new NukkitSender(event.getPlayer()), event.getMessage()));
    }
}
