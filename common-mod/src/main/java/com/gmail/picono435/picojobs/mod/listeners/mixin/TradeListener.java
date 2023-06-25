package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeTrigger.class)
public class TradeListener {

    @Inject(at = @At("HEAD"), method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/npc/AbstractVillager;Lnet/minecraft/world/item/ItemStack;)V")
    public void trigger(ServerPlayer serverPlayer, AbstractVillager abstractVillager, ItemStack itemStack, CallbackInfo ci) {
        WorkListener.simulateWorkListener(new ModSender(serverPlayer), Type.TRADE, itemStack.getCount(), itemStack.getItem());
    }

}
