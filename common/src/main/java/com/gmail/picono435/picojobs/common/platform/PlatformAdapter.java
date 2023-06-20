package com.gmail.picono435.picojobs.common.platform;

import java.util.UUID;

public interface PlatformAdapter {

    UUID getPlayerUUID(String playername);

    String getPlatformVersion();

    String getMinecraftVersion();

    String getPort();

    boolean isPluginEnabled(String plugin);
}
