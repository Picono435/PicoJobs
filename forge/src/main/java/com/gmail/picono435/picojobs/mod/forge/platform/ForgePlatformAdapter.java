package com.gmail.picono435.picojobs.mod.forge.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ForgePlatformAdapter implements PlatformAdapter {
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
        return FMLLoader.versionInfo().forgeVersion();
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
        return ModList.get().getModFileById(plugin.toLowerCase()) != null;
    }

    @Override
    public List<String> getPlayerList() {
        return List.of(PicoJobsMod.getServer().get().getPlayerNames());
    }
}
