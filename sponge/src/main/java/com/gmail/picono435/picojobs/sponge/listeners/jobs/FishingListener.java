package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.event.filter.cause.First;

public class FishingListener {

    @Listener
    public void onFishing(FishingEvent.Stop event, @First ServerPlayer player) {
        if(event.transactions().size() <= 0) return;
        //System.out.println(event.transactions().get(0).finalReplacement().type());
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.FISHING, event.transactions().get(0).finalReplacement().type());
    }
}
