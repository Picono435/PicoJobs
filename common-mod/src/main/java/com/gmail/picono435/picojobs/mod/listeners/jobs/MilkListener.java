package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class MilkListener implements PlayerEvent.FillBucket {
    @Override
    public CompoundEventResult<ItemStack> fill(Player player, Level level, ItemStack stack, @Nullable HitResult target) {
        if(target == null || target.getType() != HitResult.Type.ENTITY) return CompoundEventResult.pass();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return CompoundEventResult.pass();
        if(!jp.isWorking()) return CompoundEventResult.pass();
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.MILK)) return CompoundEventResult.pass();

        if(!job.inWhitelist(Type.MILK, ((EntityHitResult) target).getEntity())) return CompoundEventResult.pass();

        if(!jp.isInWorkZone(player.getUUID())) return CompoundEventResult.pass();
        if(jp.simulateEvent()) {
            player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
        }
        return CompoundEventResult.pass();
    }
}
