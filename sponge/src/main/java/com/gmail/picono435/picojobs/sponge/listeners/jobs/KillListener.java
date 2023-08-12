package com.gmail.picono435.picojobs.sponge.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class KillListener {

    @Listener
    public void onKillEntity(DestructEntityEvent.Death event) {
        if(!(event.entity() instanceof Player)) return;
        if(!event.entity().get(Keys.LAST_ATTACKER).isPresent()) return;
        if(!(event.entity().get(Keys.LAST_ATTACKER).get() instanceof Player)) return;
        WorkListener.simulateWorkListener(new SpongeSender((Player) event.entity().get(Keys.LAST_ATTACKER).get()), Type.KILL_ENTITY, event.entity().type());
    }
}
