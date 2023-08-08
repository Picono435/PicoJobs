package com.gmail.picono435.picojobs.sponge;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.sponge.command.SpongeJobsAdminCommand;
import com.gmail.picono435.picojobs.sponge.command.SpongeJobsCommand;
import com.gmail.picono435.picojobs.sponge.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.sponge.platform.*;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.placeholder.PlaceholderParsers;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.plugin.PluginCandidate;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.JVMPluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.plugin.builtin.jvm.locator.JVMPluginResource;

import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Plugin("picojobs")
public class PicoJobsSponge {

    private static PicoJobsSponge instance;
    private final Logger logger;
    private final Game game;
    private PluginContainer pluginContainer;
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
        URL jarURL = null;
        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(logger.getName());
        try {
            Field privateField = JVMPluginContainer.class.getDeclaredField("candidate");
            privateField.setAccessible(true);
            PluginCandidate<JVMPluginResource> candidate = (PluginCandidate<JVMPluginResource>) privateField.get(event.plugin());
            jarURL = candidate.resource().path().toUri().toURL();
        } catch (Exception e) {
            slf4jLogger.error("Unfortunately PicoJobs sponge version does not work with sponge. To use FORGE please install the forge version of the plugin.");
            e.printStackTrace();
        }
        PicoJobsCommon.onLoad(
                event.plugin().metadata().version().getQualifier(),
                Platform.SPONGE,
                slf4jLogger,
                configFilePath.getParent().toFile(),
                null,
                new SpongeSchedulerAdapter(game, event.plugin()),
                new SpongePlatformAdapter(),
                new SpongeColorConverter(),
                new SpongePlaceholderTranslator(),
                new SpongeWhitelistConverter(),
                new SpongeSoftwareHooker(),
                jarURL
            );
        this.pluginContainer = event.plugin();
    }

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        Stream<PlaceholderParser> placeholders = PlaceholderParsers.registry().stream();
        placeholders.forEach((parser) -> {
            String placeholder = "%" + parser.key(RegistryTypes.PLACEHOLDER_PARSER).asString().replace("_", "_").replace(":", "_") + "%";
            if(placeholder.equals("%sponge_name%")) placeholder = "%player_name%";
            logger.error(placeholder);
            placeholderParsers.put(placeholder, parser);
        });
        PicoJobsCommon.onEnable();
    }

    @Listener
    public void onRegisterRawCommands(final RegisterCommandEvent<Command.Raw> event ){
        List<String> jobsAliases = LanguageManager.getCommandAliases("jobs");
        String jobFirstAliase = jobsAliases.get(0);
        jobsAliases.remove(0);
        event.register(this.pluginContainer, new SpongeJobsCommand(), jobFirstAliase, jobsAliases.toArray(new String[0]));

        List<String> jobsAdminAliases = LanguageManager.getCommandAliases("jobsadmin");
        String jobAdminFirstAliase = jobsAdminAliases.get(0);
        jobsAliases.remove(0);
        event.register(this.pluginContainer, new SpongeJobsAdminCommand(), jobAdminFirstAliase, jobsAdminAliases.toArray(new String[0]));
    }

    @Listener
    public void onRegisterPlaceholders(final RegisterRegistryValueEvent event) {
        System.out.println("REGISTERING PLACEHOLDERS");
        PlaceholderAPIHook.registerPlaceholders();
    }

    @Listener
    public void onServerStop(final StoppingEngineEvent<Server> event) {
        PicoJobsCommon.onDisable();
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
