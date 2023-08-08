package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.command.api.Sender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

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
        //TODO: this...
    }

    @Override
    public void closeInventory() {
        ((ServerPlayer) sender).closeInventory();
    }

    @Override
    public void dispatchCommand(String command) {

    }
}
