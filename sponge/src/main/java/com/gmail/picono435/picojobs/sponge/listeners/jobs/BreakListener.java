package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;

public class BreakListener {

    @Listener
    public void onBreakBlock(ChangeBlockEvent.Post event, @First ServerPlayer player) {
        if(event.receipts().get(0).operation() != Operations.BREAK.get()) return;
        BlockType blockType = event.receipts().get(0).originalBlock().state().type();
        int isNatural = player.world().get(event.receipts().get(0).originalBlock().position(), PicoJobsSponge.PLACED_BLOCK).orElse(0);
        if(isNatural != 0) return;
        System.out.println(isNatural);
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.BREAK, blockType);
        player.world().offer(event.receipts().get(0).finalBlock().position(), PicoJobsSponge.PLACED_BLOCK, 0);
    }

    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Post event, @First ServerPlayer player) {
        if(event.receipts().get(0).operation() != Operations.PLACE.get()) return;
        System.out.println(event.receipts().get(0).finalBlock().state().type());
        DataTransactionResult result = player.world().offer(event.receipts().get(0).finalBlock().position(), PicoJobsSponge.PLACED_BLOCK, 1);
        System.out.println(result.toString());
        System.out.println(player.world().get(event.receipts().get(0).finalBlock().position(), PicoJobsSponge.PLACED_BLOCK) + " YO");
    }
}
