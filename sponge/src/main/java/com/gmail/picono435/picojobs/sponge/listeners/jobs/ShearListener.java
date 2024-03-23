package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.entity.living.animal.Sheep;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventContextKeys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;

import java.util.Arrays;

public class ShearListener {

    @Listener
    public void onShear(InteractEntityEvent.Secondary.On event, @First ServerPlayer player) {
        if(!(event.entity() instanceof Sheep)) return;
        if(!event.context().get(EventContextKeys.USED_ITEM).isPresent()) return;
        if(event.context().get(EventContextKeys.USED_ITEM).get().type() != ItemTypes.SHEARS.get()) return;
        /*System.out.println(Arrays.toString(event.cause().all().toArray()));
        System.out.println(Arrays.toString(event.context().keySet().toArray()));
        System.out.println(Arrays.toString(event.transactions().toArray()));*/
        WorkListener.simulateWorkListener(new SpongeSender(player), Type.SHEAR, ((Sheep) event.entity()).color().get());
    }
}
