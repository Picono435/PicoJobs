package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlaceListener implements BlockEvent.Place {
    @Override
    public EventResult placeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
        if(!(placer instanceof Player)) return EventResult.pass();
        Player player = (Player) placer;
        WorkListener.simulateWorkListener(new ModSender(player), Type.PLACE, state.getBlock());
        return EventResult.pass();
    }
}
