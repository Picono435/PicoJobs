package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.container.ClickContainerEvent;
import org.spongepowered.api.item.inventory.ContainerTypes;

public class TradeListener {

    @Listener
    public void onTrade(ClickContainerEvent event, @First ServerPlayer player) {
        if(event.container().type() != ContainerTypes.MERCHANT.get()) return;
        if(event.transactions().get(0).slot().get(Keys.SLOT_INDEX).get() != 2) return;
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.TRADE, event.transactions().get(0).original().quantity(), event.transactions().get(0).original().type());
    }
}
