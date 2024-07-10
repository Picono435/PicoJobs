package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class EnchantListener {

    @Inject(at = @At("HEAD"), method = "onEnchantmentPerformed(Lnet/minecraft/world/item/ItemStack;I)V")
    public void onEnchantmentPerformed(ItemStack itemStack, int i, CallbackInfo ci) {
        Player player = (Player) ((Object)this);
        WorkListener.simulateWorkListener(new ModSender(player), Type.ENCHANTING, itemStack.getCount(), itemStack.getItem());
    }
}
