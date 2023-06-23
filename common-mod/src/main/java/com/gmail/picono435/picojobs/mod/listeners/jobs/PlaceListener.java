package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlaceListener implements BlockEvent.Place {
    @Override
    public EventResult placeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
        if(!(placer instanceof Player)) return EventResult.pass();
        Player player = (Player) placer;
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return EventResult.pass();
        if(!jp.isWorking()) return EventResult.pass();
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.PLACE)) return EventResult.pass();
        if(!jp.isInWorkZone(player.getUUID())) return EventResult.pass();

        if(!job.inWhitelist(Type.PLACE, state.getBlock())) return EventResult.pass();

        if(jp.simulateEvent()) {
            player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
        }
        return EventResult.pass();
    }
}
