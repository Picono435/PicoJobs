package com.gmail.picono435.picojobs.mod.listeners;

import com.gmail.picono435.picojobs.common.listeners.JoinCacheListener;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;

public class ModJoinCacheListener implements PlayerEvent.PlayerJoin {
    @Override
    public void join(ServerPlayer player) {
        JoinCacheListener.onJoin(player.getUUID());
    }
}
