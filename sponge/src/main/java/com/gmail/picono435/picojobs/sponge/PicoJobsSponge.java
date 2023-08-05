package com.gmail.picono435.picojobs.sponge;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.sponge.platform.*;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.placeholder.PlaceholderParsers;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Plugin("picojobs")
public class PicoJobsSponge {

    private static PicoJobsSponge instance;
    private final Logger logger;
    private final Game game;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFilePath;

    private Map<String, PlaceholderParser> placeholderParsers = new HashMap<>();

    @Inject
    public PicoJobsSponge(Logger logger, Game game) {
        instance = this;
        this.logger = logger;
        this.game = game;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        if(!event.plugin().metadata().id().equals("picojobs")) return;
        PicoJobsCommon.onLoad(
                event.plugin().metadata().version().getQualifier(),
                Platform.SPONGE,
                LoggerFactory.getLogger(logger.getName()),
                configFilePath.toFile(),
                null,
                new SpongeSchedulerAdapter(game, event.plugin()),
                new SpongePlatformAdapter(),
                new SpongeColorConverter(),
                new SpongePlaceholderTranslator(),
                new SpongeWhitelistConverter(),
                new SpongeSoftwareHooker()
        );
    }

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        Stream<PlaceholderParser> placeholders = PlaceholderParsers.registry().stream();
        placeholders.forEach((parser) -> {
            String placeholder = "%" + parser.key(RegistryTypes.PLACEHOLDER_PARSER).asString().replace("_", "-").replace(":", "_") + "%";
            logger.error(placeholder);
            placeholderParsers.put(placeholder, parser);
        });
    }

    public static PicoJobsSponge getInstance() {
        return instance;
    }

    public Game getGame() {
        return game;
    }

    public Map<String, PlaceholderParser> getPlaceholderParsers() {
        return placeholderParsers;
    }
}
