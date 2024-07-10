package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MoveListener {

    @Inject(at = @At("HEAD"), method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V")
    public void move(MoverType moverType, Vec3 vec3, CallbackInfo ci) {
        if((Object)this instanceof Player) {
            Player player = (Player) ((Object) this);
            long distance = Math.round(Math.floor(vec3.distanceTo(player.position())));
            WorkListener.simulateWorkListener(new ModSender(player), Type.MOVE, (int)distance, player.level().getBlockState(BlockPos.containing(vec3)).getBlock());
        }
    }
}
