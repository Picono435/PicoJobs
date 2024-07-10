package com.gmail.picono435.picojobs.nukkit.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import com.gmail.picono435.picojobs.common.listeners.ExecuteCommandListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class NukkitExecuteCommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        ExecuteCommandListener.onCommand(new NukkitSender(event.getPlayer()), event.getMessage());
    }
}
