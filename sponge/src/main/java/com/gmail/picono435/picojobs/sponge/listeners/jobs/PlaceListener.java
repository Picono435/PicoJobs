package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

public class PlaceListener {

    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Post event, @First ServerPlayer player) {
        if(event.receipts().get(0).operation() != Operations.PLACE.get()) return;
        BlockType blockType = event.receipts().get(0).finalBlock().state().type();
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.PLACE, blockType);
    }
}
