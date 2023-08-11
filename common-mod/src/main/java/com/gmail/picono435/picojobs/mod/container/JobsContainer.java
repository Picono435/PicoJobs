package com.gmail.picono435.picojobs.mod.container;

import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.mod.platform.ModInventoryAdapter;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

public class JobsContainer extends SimpleContainer {

    private ModInventoryAdapter inventoryAdapter;

    public JobsContainer(int i, ModInventoryAdapter inventoryAdapter) {
        super(i);
        this.inventoryAdapter = inventoryAdapter;
    }

    public boolean simulateClick(int slot, Player player) {
        return InventoryMenuListener.onBasicClick(new ModSender(player), this.inventoryAdapter, this.getItem(slot));
    }
}
