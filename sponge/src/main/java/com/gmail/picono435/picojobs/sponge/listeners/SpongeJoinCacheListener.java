package com.gmail.picono435.picojobs.sponge.listeners;

import com.gmail.picono435.picojobs.common.listeners.JoinCacheListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public class SpongeJoinCacheListener {

    @Listener
    public void onJoin(ServerSideConnectionEvent.Join event) {
        JoinCacheListener.onJoin(event.player().uniqueId());
    }
}
