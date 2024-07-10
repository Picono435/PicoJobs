package com.gmail.picono435.picojobs.mod.listeners.mixin;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.mod.container.JobsContainer;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class InventoryMenuMixin {

    @Inject(at = @At("HEAD"), method = "doClick(IILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V", cancellable = true)
    public void doClick(int slot, int j, ClickType clickType, Player player, CallbackInfo ci) {
        if(!((AbstractContainerMenu)((Object)this) instanceof ChestMenu)) return;
        ChestMenu chestMenu = (ChestMenu) ((Object)this);
        if(chestMenu.getContainer() instanceof JobsContainer) {
            JobsContainer jobsContainer = (JobsContainer) chestMenu.getContainer();
            if(slot > jobsContainer.getContainerSize() - 1) {
                ci.cancel();
            } else {
                if(jobsContainer.simulateClick(slot, player)) ci.cancel();
            }
        }
    }
}
