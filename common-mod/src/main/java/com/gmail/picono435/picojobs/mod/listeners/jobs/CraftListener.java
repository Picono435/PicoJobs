package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CraftListener implements PlayerEvent.CraftItem {
    @Override
    public void craft(Player player, ItemStack constructed, Container inventory) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUUID());
        if(!jp.hasJob()) return;
        if(!jp.isWorking()) return;
        Job job = jp.getJob();
        if(!job.getTypes().contains(Type.CRAFT)) return;
        if(!jp.isInWorkZone(player.getUUID())) return;

        if(!job.inWhitelist(Type.CRAFT, constructed.getItem())) return;

        for(int i = 0; i < constructed.getCount(); i++) {
            if(jp.simulateEvent()) {
                player.sendSystemMessage(Component.literal(LanguageManager.getMessage("finished-work", player.getUUID())));
            }
        }
    }
}
