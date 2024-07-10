package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class StripLogsListener {

    @Inject(at = @At(value = "INVOKE", opcode = 0, target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;")
    public void useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = useOnContext.getPlayer();
        if (player instanceof ServerPlayer) {
            Block block = ((AxeItem)((Object) this)).getStripped(useOnContext.getLevel().getBlockState(useOnContext.getClickedPos())).get().getBlock();
            WorkListener.simulateWorkListener(new ModSender(player), Type.STRIP_LOGS, block);
        }
    }
}
