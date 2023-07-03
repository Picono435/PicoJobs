package com.gmail.picono435.picojobs.nukkit.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryClickEvent;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitInventoryAdapter;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitSender;

public class NukkitInventoryMenuListener implements Listener {

    @EventHandler()
    public void onBasicClick(InventoryClickEvent event) {
        if(event.getHeldItem() == null) return;
        event.setCancelled(InventoryMenuListener.onBasicClick(new NukkitSender(event.getPlayer()),
                new NukkitInventoryAdapter(event.getInventory(), event.getInventory().getTitle()),
                event.getSlot(),
                event.getHeldItem()));
    }
}
