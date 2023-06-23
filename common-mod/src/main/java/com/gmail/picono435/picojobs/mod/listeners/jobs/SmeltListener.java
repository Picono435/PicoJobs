package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SmeltListener implements PlayerEvent.SmeltItem {
    @Override
    public void smelt(Player player, ItemStack smelted) {
        WorkListener.simulateWorkListener(new ModSender(player), Type.SMELT, smelted.getCount(), smelted.getItem());
    }
}
