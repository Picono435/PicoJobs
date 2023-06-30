package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.data.PlacedBlocksData;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BreakListener implements BlockEvent.Break, BlockEvent.Place {

    @Override
    public EventResult breakBlock(Level level, BlockPos pos, BlockState state, ServerPlayer player, @Nullable IntValue xp) {
        boolean isNaturalBlock = !player.serverLevel().getDataStorage().computeIfAbsent(PlacedBlocksData::load, PlacedBlocksData::create, "placedblocks").isPlacedBlock(pos);
        if(!isNaturalBlock) player.serverLevel().getDataStorage().computeIfAbsent(PlacedBlocksData::load, PlacedBlocksData::create, "placedblocks").removePlacedBlock(pos);
        if(state.getBlock() instanceof CropBlock) {
            CropBlock cropBlock = (CropBlock) state.getBlock();
            if (cropBlock.getAge(state) == CropBlock.MAX_AGE) return EventResult.pass();
            isNaturalBlock = true;
        }
        if(!isNaturalBlock) return EventResult.pass();
        WorkListener.simulateWorkListener(new ModSender(player), Type.BREAK, state.getBlock());
        return EventResult.pass();
    }

    @Override
    public EventResult placeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
        if(placer instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) placer;
            serverPlayer.serverLevel().getDataStorage().computeIfAbsent(PlacedBlocksData::load, PlacedBlocksData::create, "placedblocks").addPlacedBlock(pos);
        }
        return EventResult.pass();
    }
}
