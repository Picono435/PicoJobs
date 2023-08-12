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

public class RepairListener {

    @Listener
    public void onRepair(ClickContainerEvent event, @First ServerPlayer player) {
        if(event.container().type() != ContainerTypes.ANVIL.get()) return;
        if(event.slot().get().get(Keys.SLOT_INDEX).get() != 2) return;
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.REPAIR, event.container().cursor().get().quantity(), event.container().cursor().get().type());
    }
}
