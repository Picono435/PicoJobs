package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.EnchantItemEvent;

public class EnchantListener {

    @Listener
    public void onEnchant(EnchantItemEvent.Post event, @First ServerPlayer player) {
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.ENCHANTING, event.enchantingSlot().peek());
    }
}
