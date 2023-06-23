package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SmeltListener implements PlayerEvent.SmeltItem {
    @Override
    public void smelt(Player player, ItemStack smelted) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return;
        if(!jp.isWorking()) return;
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.SMELT)) return;
        if(!jp.isInWorkZone(player.getUUID())) return;

        if(!job.inWhitelist(Type.SMELT, smelted.getItem())) return;

        for(int i = 0; i < smelted.getCount(); i++) {
            if(jp.simulateEvent()) {
                player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
            }
        }
    }
}
