package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;

public class MoveListener {

    @Listener
    public void onMove(MoveEntityEvent event, @Getter("entity") ServerPlayer player) {
        long distance = Math.round(Math.floor(event.destinationPosition().distance(event.originalDestinationPosition())));
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.MOVE, (int)distance, event.entity().world().block(event.destinationPosition().toInt()).type());
    }
}
