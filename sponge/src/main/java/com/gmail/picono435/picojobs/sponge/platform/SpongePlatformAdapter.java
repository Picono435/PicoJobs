package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import org.spongepowered.api.Sponge;

import java.util.UUID;

public class SpongePlatformAdapter implements PlatformAdapter {
    @Override
    public UUID getPlayerUUID(String playername) {
        return PicoJobsSponge.getInstance().getGame().server().player(playername).get().uniqueId();
    }

    @Override
    public String getPlatformVersion() {
        return null;
    }

    @Override
    public String getMinecraftVersion() {
        return Sponge.platform().minecraftVersion().name();
    }

    @Override
    public String getPort() {
        return null;
    }

    @Override
    public boolean isPluginEnabled(String plugin) {
        return Sponge.pluginManager().plugin(plugin).isPresent();
    }
}
