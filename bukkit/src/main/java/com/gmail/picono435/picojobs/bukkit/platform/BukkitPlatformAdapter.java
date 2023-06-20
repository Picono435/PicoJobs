package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class BukkitPlatformAdapter implements PlatformAdapter {
    @Override
    public UUID getPlayerUUID(String playername) {
        if(Bukkit.getPlayer(playername) == null) return null;
        return Bukkit.getPlayer(playername).getUniqueId();
    }

    @Override
    public String getPlatformVersion() {
        return Bukkit.getVersion();
    }

    @Override
    public String getMinecraftVersion() {
        String serverVersionString = Bukkit.getBukkitVersion();
        int spaceIndex = serverVersionString.indexOf("-");
        serverVersionString = serverVersionString.substring(0, spaceIndex);
        return serverVersionString;
    }

    @Override
    public String getPort() {
        return String.valueOf(Bukkit.getPort());
    }

    @Override
    public boolean isPluginEnabled(String pluginString) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginString);
        return plugin != null && plugin.isEnabled();
    }
}
