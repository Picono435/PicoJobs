package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.server.ServerLocation;

public class BreakListener {

    @Listener
    public void onBreakBlock(ChangeBlockEvent.Post event, @First ServerPlayer player) {
        if(event.receipts().get(0).operation() != Operations.BREAK.get()) return;
        BlockType blockType = event.receipts().get(0).originalBlock().state().type();
        if(ServerLocation.of(event.world(), event.receipts().get(0).originalBlock().position()).get(Keys.CREATOR).isPresent()) return;
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.BREAK, blockType);
    }
}
