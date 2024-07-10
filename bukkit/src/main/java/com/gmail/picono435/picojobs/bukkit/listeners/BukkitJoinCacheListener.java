package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.common.listeners.JoinCacheListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BukkitJoinCacheListener implements Listener {

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        JoinCacheListener.onJoin(event.getPlayer().getUniqueId());
    }
}
