package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class RepairListener {

    @Inject(at = @At("HEAD"), method = "onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V")
    public void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        WorkListener.simulateWorkListener(new ModSender(player), Type.REPAIR, itemStack.getItem());
    }
}
