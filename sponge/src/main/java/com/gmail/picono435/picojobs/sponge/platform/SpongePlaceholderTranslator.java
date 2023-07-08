package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import me.rojo8399.placeholderapi.impl.PlaceholderAPIPlugin;
import me.rojo8399.placeholderapi.impl.PlaceholderServiceImpl;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.UUID;

public class SpongePlaceholderTranslator implements PlaceholderTranslator {
    @Override
    public String setPlaceholders(UUID player, String string) {
        Player playerInstance = PicoJobsSponge.getInstance().getGame().server().player(player).get();
        return PlaceholderServiceImpl.get().replacePlaceholders(string, playerInstance, playerInstance).toString();
    }

    @Override
    public List<String> setPlaceholders(UUID player, List<String> stringList) {
        return null;
    }
}
