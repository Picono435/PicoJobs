package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<String> getPlayerList() {
        return PicoJobsNukkit.getInstance().getServer().getOnlinePlayers().values().stream().map(Player::getName).collect(Collectors.toList());
    }
}
