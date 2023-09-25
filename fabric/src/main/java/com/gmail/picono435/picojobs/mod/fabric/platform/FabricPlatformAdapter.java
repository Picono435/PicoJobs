package com.gmail.picono435.picojobs.mod.fabric.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FabricPlatformAdapter implements PlatformAdapter {
    @Override
    public Optional<UUID> getPlayerUUID(String playername) {
        ServerPlayer player = PicoJobsMod.getServer().get().getPlayerList().getPlayerByName(playername);
        if(player == null) {
            return Optional.empty();
        } else {
            return Optional.of(player.getUUID());
        }
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

    @Override
    public List<String> getPlayerList() {
        return List.of(PicoJobsMod.getServer().get().getPlayerNames());
    }
}
