package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.inventory.ChooseJobMenu;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NukkitSender implements Sender {
    private CommandSender sender;

    public NukkitSender(CommandSender sender) {
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
            player.addWindow(((NukkitInventoryAdapter) ChooseJobMenu.createMenu(new NukkitInventoryAdapter(), player.getUniqueId())).getInventory());
        } else {
            player.addWindow(((NukkitInventoryAdapter) WorkMenu.createMenu(new NukkitInventoryAdapter(), player.getUniqueId(), inventory)).getInventory());
        }
    }

    @Override
    public void closeInventory() {
        if(!isPlayer()) return;
        PicoJobsCommon.getSchedulerAdapter().asyncLater(() -> ((Player) sender).getInventory().close((Player) sender), 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void dispatchCommand(String command) {
        PicoJobsNukkit.getInstance().getServer().dispatchCommand(sender, command);
    }
}
