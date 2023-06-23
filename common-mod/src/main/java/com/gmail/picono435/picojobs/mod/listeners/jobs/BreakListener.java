package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BreakListener implements BlockEvent.Break {
    @Override
    public EventResult breakBlock(Level level, BlockPos pos, BlockState state, ServerPlayer player, @Nullable IntValue xp) {
        /*JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
        if(!jp.hasJob()) return EventResult.pass();
        if(!jp.isWorking()) return EventResult.pass();
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.BREAK)) return EventResult.pass();
        if(!jp.isInWorkZone(player.getUUID())) return EventResult.pass();
        boolean isNaturalBlock = event.getBlock().getMetadata("PLACED").size() == 0;
        if(PicoJobsCommon.isLessThan("1.13.2")) {
            if(event.getBlock().getState().getData() instanceof Crops) {
                if (((Crops) event.getBlock().getState().getData()).getState() != CropState.RIPE) return EventResult.pass();
                isNaturalBlock = true;
            }
        } else {
            if(event.getBlock().getBlockData() instanceof Ageable) {
                Ageable ageable = (Ageable) event.getBlock().getBlockData();
                if(ageable.getMaximumAge() != ageable.getAge()) return EventResult.pass();
                isNaturalBlock = true;
            }
        }
        if(!isNaturalBlock) return EventResult.pass();

        if(!job.inWhitelist(Type.BREAK, event.getBlock().getType())) return EventResult.pass();

        if(jp.simulateEvent()) {
            player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
        }*/
        return EventResult.pass();
    }
}
