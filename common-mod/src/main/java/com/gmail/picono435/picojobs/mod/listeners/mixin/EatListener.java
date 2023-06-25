package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class EatListener {

    @Inject(at = @At("HEAD"), method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;")
    public void eat(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        Player player = (Player) ((Object)this);
        if(!level.isClientSide) WorkListener.simulateWorkListener(new ModSender(player), Type.EAT, itemStack.getCount(), itemStack.getItem());
    }
}
