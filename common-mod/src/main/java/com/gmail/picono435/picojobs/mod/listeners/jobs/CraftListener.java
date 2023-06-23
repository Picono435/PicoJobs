package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CraftListener implements PlayerEvent.CraftItem {
    @Override
    public void craft(Player player, ItemStack constructed, Container inventory) {
        WorkListener.simulateWorkListener(new ModSender(player), Type.CRAFT, constructed.getCount(), constructed.getItem());
    }
}
