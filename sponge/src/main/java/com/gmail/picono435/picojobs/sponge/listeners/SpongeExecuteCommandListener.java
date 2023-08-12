package com.gmail.picono435.picojobs.sponge.listeners;

import com.gmail.picono435.picojobs.common.listeners.ExecuteCommandListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

public class SpongeExecuteCommandListener {

    @Listener
    public void onCommand(ExecuteCommandEvent.Pre event, @First ServerPlayer player) {
        ExecuteCommandListener.onCommand(new SpongeSender(player), '/' + event.command());
    }
}
