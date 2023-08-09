package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.inventory.ChooseJobMenu;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitSender implements Sender {

    private CommandSender sender;

    public BukkitSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean sendMessage(String message) {
        sender.sendMessage(message);
        return true;
    }

    @Override
    public UUID getUUID() {
        if(this.isPlayer()) return ((Player)this.sender).getUniqueId();
        return null;
    }

    @Override
    public void openInventory(String inventory) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        if(inventory.equals("choose-job")) {
            player.openInventory(((BukkitInventoryAdapter) ChooseJobMenu.createMenu(new BukkitInventoryAdapter(), this.getUUID())).getInventory());
        } else {
            player.openInventory(((BukkitInventoryAdapter)  WorkMenu.createMenu(new BukkitInventoryAdapter(), this.getUUID(), inventory)).getInventory());
        }
    }

    @Override
    public void closeInventory() {
        if(!isPlayer()) return;
        ((Player) sender).closeInventory();
    }

    @Override
    public void dispatchCommand(String command) {
        Bukkit.dispatchCommand(sender, command);
    }
}
