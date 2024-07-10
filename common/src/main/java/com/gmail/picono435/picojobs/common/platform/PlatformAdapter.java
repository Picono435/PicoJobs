package com.gmail.picono435.picojobs.common.platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformAdapter {

    Optional<UUID> getPlayerUUID(String playername);

    String getPlatformVersion();

    String getMinecraftVersion();

    String getPort();

    boolean isPluginEnabled(String plugin);

    List<String> getPlayerList();
}
