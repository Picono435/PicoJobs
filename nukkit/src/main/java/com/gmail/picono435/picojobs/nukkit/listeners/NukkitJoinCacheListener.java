package com.gmail.picono435.picojobs.nukkit.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import com.gmail.picono435.picojobs.common.listeners.JoinCacheListener;

public class NukkitJoinCacheListener implements Listener {

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        JoinCacheListener.onJoin(event.getPlayer().getUniqueId());
    }
}
