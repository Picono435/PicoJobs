package com.gmail.picono435.picojobs.common.command.api;

import java.util.UUID;

public interface Sender {

    boolean isPlayer();

    boolean hasPermission(String string);

    boolean sendMessage(String message);

    UUID getUUID();

    void openInventory(String inventory);
}
