package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.plugin.Plugin;
import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.UUID;

public class NukkitPlatformAdapter implements PlatformAdapter {
    @Override
    public UUID getPlayerUUID(String playername) {
        if(PicoJobsNukkit.getInstance().getServer().getPlayerExact(playername) == null) return null;
        return PicoJobsNukkit.getInstance().getServer().getPlayerExact(playername).getUniqueId();
    }

    @Override
    public String getPlatformVersion() {
        return PicoJobsNukkit.getInstance().getServer().getNukkitVersion();
    }

    @Override
    public String getMinecraftVersion() {
        return PicoJobsNukkit.getInstance().getServer().getVersion();
    }

    @Override
    public String getPort() {
        return String.valueOf(PicoJobsNukkit.getInstance().getServer().getPort());
    }

    @Override
    public boolean isPluginEnabled(String pluginString) {
        Plugin plugin = PicoJobsNukkit.getInstance().getServer().getPluginManager().getPlugin(pluginString);
        return plugin != null && plugin.isEnabled();
    }
}
