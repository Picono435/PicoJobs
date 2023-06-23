package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KillEntityListener implements EntityEvent.LivingDeath {
    @Override
    public EventResult die(LivingEntity entity, DamageSource source) {
        if(!source.is(DamageTypes.PLAYER_ATTACK)) return EventResult.pass();
        Player player = (Player) source.getEntity();
        WorkListener.simulateWorkListener(new ModSender(player), Type.KILL_ENTITY, entity.getType());
        return EventResult.pass();
    }
}
