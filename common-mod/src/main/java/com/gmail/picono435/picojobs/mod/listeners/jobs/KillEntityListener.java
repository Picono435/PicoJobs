package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KillEntityListener implements EntityEvent.LivingDeath {
    @Override
    public EventResult die(LivingEntity entity, DamageSource source) {
        if(!source.is(DamageTypes.PLAYER_ATTACK)) return EventResult.pass();
        Player player = (Player) source.getEntity();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return EventResult.pass();
        if(!jp.isWorking()) return EventResult.pass();
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.KILL_ENTITY)) return EventResult.pass();
        if(!jp.isInWorkZone(player.getUUID())) return EventResult.pass();

        if(!job.inWhitelist(Type.KILL_ENTITY, entity.getType())) return EventResult.pass();

        if(jp.simulateEvent()) {
            player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
        }
        return EventResult.pass();
    }
}
