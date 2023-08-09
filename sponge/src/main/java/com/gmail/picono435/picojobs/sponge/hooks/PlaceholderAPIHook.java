package com.gmail.picono435.picojobs.sponge.hooks;

import com.gmail.picono435.picojobs.api.JobPlaceholders;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.placeholder.PlaceholderParsers;

public class PlaceholderAPIHook {

    private static boolean alreadyRegistered;

    public static void registerPlaceholders() {
        PicoJobsCommon.getLogger().error("So registering placeholders innit...");
        if(alreadyRegistered) return;
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "job"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "job"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "tag"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "tag"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "work"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "work"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "reqmethod"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "reqmethod"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "salary"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "salary"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "level"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "level"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        PlaceholderParsers.registry().register(ResourceKey.of("jobplayer", "working"),
                PlaceholderParser.builder().parser((context) -> context.associatedObject()
                        .filter(x -> x instanceof Player)
                        .map(player -> JobPlaceholders.translatePlaceholders(((Player) player).uniqueId(), "working"))
                        .map(string -> Component.text(string))
                        .orElse(Component.empty())).build());
        alreadyRegistered = true;
    }
}
