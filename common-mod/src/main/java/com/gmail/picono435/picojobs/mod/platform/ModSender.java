package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.inventory.ChooseJobMenu;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import com.gmail.picono435.picojobs.mod.PicoJobsExpected;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ModSender implements Sender {
    private CommandSource commandSource;

    public ModSender(CommandSource commandSource) {
        this.commandSource = commandSource;
    }

    @Override
    public boolean isPlayer() {
        return commandSource instanceof Player;
    }

    @Override
    public boolean hasPermission(String permission) {
        if(!isPlayer()) return true;
        return PicoJobsExpected.hasPermission((Player) commandSource, permission);
    }

    public static boolean hasPermissionStatic(CommandSource commandSource, String permission) {
        if(!(commandSource instanceof Player)) return true;
        return PicoJobsExpected.hasPermission((Player) commandSource, permission);
    }

    @Override
    public boolean sendMessage(String message) {
        commandSource.sendSystemMessage(Component.literal(message));
        return true;
    }

    @Override
    public UUID getUUID() {
        if(isPlayer()) return ((Player) commandSource).getUUID();
        return null;
    }

    @Override
    public void openInventory(String inventory) {
        //TODO: Open inventory
        if(!isPlayer()) return;
        Player player = (Player) commandSource;
        final ModInventoryAdapter[] modInventoryAdapter = {null};
        MenuProvider menuProvider;
        if(inventory.equals("choose-job")) {
            menuProvider = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal(modInventoryAdapter[0].getTitle());
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory minecraftInventory, Player player) {
                    modInventoryAdapter[0] = (ModInventoryAdapter) ChooseJobMenu.createMenu(new ModInventoryAdapter(minecraftInventory, i), player.getUUID());
                    return modInventoryAdapter[0].getChestMenu();
                }
            };
        } else {
            menuProvider = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal(modInventoryAdapter[0].getTitle());
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory minecraftInventory, Player player) {
                    modInventoryAdapter[0] = (ModInventoryAdapter) WorkMenu.createMenu(new ModInventoryAdapter(minecraftInventory, i), player.getUUID(), inventory);
                    return modInventoryAdapter[0].getChestMenu();
                }
            };
        }
        player.openMenu(menuProvider);
    }

    @Override
    public void closeInventory() {
        if(!isPlayer()) return;
        ((Player) commandSource).closeContainer();
    }

    @Override
    public void dispatchCommand(String command) {
        //TODO: DISPATCH COMMAND
    }
}
