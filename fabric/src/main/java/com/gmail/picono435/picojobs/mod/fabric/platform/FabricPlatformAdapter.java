package com.gmail.picono435.picojobs.mod.fabric.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;

import java.util.UUID;

public class FabricPlatformAdapter implements PlatformAdapter {
    @Override
    public UUID getPlayerUUID(String playername) {
        return PicoJobsMod.getServer().get().getPlayerList().getPlayerByName(playername).getUUID();
    }

    @Override
    public String getPlatformVersion() {
        return FabricLoader.getInstance().getModContainer("fabricloader").get().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public String getMinecraftVersion() {
        return PicoJobsMod.getServer().get().getServerVersion();
    }

    @Override
    public String getPort() {
        return String.valueOf(PicoJobsMod.getServer().get().getPort());
    }

    @Override
    public boolean isPluginEnabled(String plugin) {
        return FabricLoader.getInstance().isModLoaded(plugin);
    }
}
