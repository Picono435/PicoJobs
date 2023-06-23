package com.gmail.picono435.picojobs.mod.listeners.jobs;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class KillListener implements EntityEvent.LivingDeath {
    @Override
    public EventResult die(LivingEntity entity, DamageSource source) {
        return EventResult.pass();
    }
}
