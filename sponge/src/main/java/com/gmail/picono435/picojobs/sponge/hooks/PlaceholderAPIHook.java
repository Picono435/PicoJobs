package com.gmail.picono435.picojobs.sponge.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.JobPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.JobPlayerPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.placeholder.PlaceholderParsers;

import java.util.Objects;

public class PlaceholderAPIHook {

    private static boolean alreadyRegistered;

    public static void registerPlaceholders() {
        if(alreadyRegistered) return;

        for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
            for(String placeholder : extension.getPlaceholders()) {
                PlaceholderParsers.registry().register(ResourceKey.of(extension.getPrefix(), placeholder),
                        PlaceholderParser.builder().parser((context) -> context.associatedObject()
                                .filter(x -> {
                                    if(extension.getPrefix().equals(JobPlaceholders.PREFIX)) return true;
                                    return x instanceof Player;
                                })
                                .map(player -> extension.translatePlaceholders(extension.getPrefix().equals(JobPlaceholders.PREFIX) ? null : ((Player) player).uniqueId(), placeholder))
                                .map(string -> Component.text(string))
                                .orElse(Component.empty())).build());
            }
        }
        alreadyRegistered = true;
    }
}
