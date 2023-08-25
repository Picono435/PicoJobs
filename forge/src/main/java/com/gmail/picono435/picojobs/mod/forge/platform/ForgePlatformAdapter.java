package com.gmail.picono435.picojobs.mod.forge.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;

import java.util.UUID;

public class ForgePlatformAdapter implements PlatformAdapter {
    @Override
    public UUID getPlayerUUID(String playername) {
        return PicoJobsMod.getServer().get().getPlayerList().getPlayerByName(playername).getUUID();
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
}
