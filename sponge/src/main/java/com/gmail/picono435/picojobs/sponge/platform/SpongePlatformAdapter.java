package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.PlatformAdapter;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.util.Identifiable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpongePlatformAdapter implements PlatformAdapter {
    @Override
    public Optional<UUID> getPlayerUUID(String playername) {
        Optional<ServerPlayer> optional = PicoJobsSponge.getInstance().getGame().server().player(playername);
        return optional.map(Identifiable::uniqueId);
    }

    @Override
    public String getPlatformVersion() {
        return Sponge.platform().container(Platform.Component.API).metadata().version().toString();
    }

    @Override
    public String getMinecraftVersion() {
        return PicoJobsSponge.getInstance().getGame().platform().minecraftVersion().name();
    }

    @Override
    public String getPort() {
        return PicoJobsSponge.getInstance().getGame().server().boundAddress().get().getPort() + "";
    }

    @Override
    public boolean isPluginEnabled(String plugin) {
        return Sponge.pluginManager().plugin(plugin.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Override
    public List<String> getPlayerList() {
        return Sponge.server().onlinePlayers().stream().map(ServerPlayer::name).collect(Collectors.toList());
    }
}
