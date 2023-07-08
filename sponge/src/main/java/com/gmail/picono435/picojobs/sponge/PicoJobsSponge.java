package com.gmail.picono435.picojobs.sponge;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.sponge.platform.SpongeColorConverter;
import com.gmail.picono435.picojobs.sponge.platform.SpongePlaceholderTranslator;
import com.gmail.picono435.picojobs.sponge.platform.SpongePlatformAdapter;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSchedulerAdapter;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;

@Plugin("picojobs")
public class PicoJobsSponge {

    private static PicoJobsSponge instance;
    @Inject
    private Logger logger;
    @Inject
    private Game game;
    @Inject
    private PluginContainer pluginContainer;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFilePath;

    public PicoJobsSponge() {
        instance = this;
        PicoJobsCommon.onLoad(
                pluginContainer.metadata().version(),
                Platform.SPONGE,
                LoggerFactory.getLogger(logger.getName()),
                configFilePath.toFile(),
                null,
                new SpongeSchedulerAdapter(game, pluginContainer),
                new SpongePlatformAdapter(),
                new SpongeColorConverter(),
                new SpongePlaceholderTranslator(),
                new BukkitWhitelistConverter(),
                new BukkitSoftwareHooker()
        );
    }

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
    }

    public static PicoJobsSponge getInstance() {
        return instance;
    }

    public Game getGame() {
        return game;
    }
}
