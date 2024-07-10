package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BukkitPlatformAdapter implements PlatformAdapter {
    @Override
    public Optional<UUID> getPlayerUUID(String playername) {
        if(Bukkit.getPlayer(playername) == null) return Optional.empty();
        return Optional.of(Bukkit.getPlayer(playername).getUniqueId());
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

    @Override
    public List<String> getPlayerList() {
        return Bukkit.getOnlinePlayers().stream().map(Player::toString).collect(Collectors.toList());
    }
}
