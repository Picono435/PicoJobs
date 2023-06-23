package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class FillListener implements PlayerEvent.FillBucket {
    @Override
    public CompoundEventResult<ItemStack> fill(Player player, Level level, ItemStack stack, @Nullable HitResult target) {
        if(target == null || target.getType() != HitResult.Type.BLOCK) return CompoundEventResult.pass();
        WorkListener.simulateWorkListener(new ModSender(player), Type.FILL,  level.getBlockState(((BlockHitResult) target).getBlockPos()).getBlock());
        return CompoundEventResult.pass();
    }
}
