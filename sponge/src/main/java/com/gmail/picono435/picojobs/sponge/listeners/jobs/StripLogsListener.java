package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventContextKeys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.registry.RegistryTypes;

public class StripLogsListener {

    @Listener
    public void onStripLogs(ChangeBlockEvent.Post event, @First ServerPlayer player) {
        if(!event.context().get(EventContextKeys.USED_ITEM).get().type().key(RegistryTypes.ITEM_TYPE).asString().endsWith("axe")) return;
        if(!event.receipts().get(0).finalBlock().state().type().key(RegistryTypes.BLOCK_TYPE).value().startsWith("stripped")) return;
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.STRIP_LOGS, event.receipts().get(0).finalBlock().state().type());
    }
}
