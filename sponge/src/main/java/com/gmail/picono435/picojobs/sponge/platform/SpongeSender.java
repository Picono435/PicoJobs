package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.inventory.ChooseJobMenu;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.manager.CommandManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;

import java.util.UUID;

public class SpongeSender implements Sender {

    private Audience sender;

    public SpongeSender(Audience sender) {
        this.sender = sender;
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof ServerPlayer;
    }

    @Override
    public boolean hasPermission(String permission) {
        if(sender instanceof ServerPlayer) {
            return ((ServerPlayer) sender).hasPermission(permission);
        } else {
            return true;
        }
    }

    @Override
    public boolean sendMessage(String message) {
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        return true;
    }

    @Override
    public UUID getUUID() {
        if(isPlayer()) {
            return ((Player) sender).uniqueId();
        } else {
            return null;
        }
    }

    @Override
    public void openInventory(String inventory) {
        if(!(sender instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) sender;
        if(inventory.equals("choose-job")) {
            SpongeInventoryAdapter inventoryAdapter = (SpongeInventoryAdapter) ChooseJobMenu.createMenu(new SpongeInventoryAdapter(), this.getUUID());
            InventoryMenu inventoryMenu = inventoryAdapter.getViewableInventory().asMenu();
            inventoryMenu.setTitle(LegacyComponentSerializer.legacyAmpersand().deserialize(inventoryAdapter.getTitle()));
            inventoryMenu.open(player);
        } else {
            SpongeInventoryAdapter inventoryAdapter = (SpongeInventoryAdapter) WorkMenu.createMenu(new SpongeInventoryAdapter(), this.getUUID(), inventory);
            InventoryMenu inventoryMenu = inventoryAdapter.getViewableInventory().asMenu();
            inventoryMenu.setTitle(LegacyComponentSerializer.legacyAmpersand().deserialize(inventoryAdapter.getTitle()));
            inventoryMenu.open(player);
        }
    }

    @Override
    public void closeInventory() {
        ((ServerPlayer) sender).closeInventory();
    }

    @Override
    public void dispatchCommand(String command) {
        try {
            if(isPlayer()) {
                Sponge.server().commandManager().process((ServerPlayer) sender, command);
            } else {
                Sponge.server().commandManager().process(Sponge.systemSubject(), command);
            }
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }
}
