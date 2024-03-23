package com.gmail.picono435.picojobs.sponge;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.sponge.command.SpongeJobsAdminCommand;
import com.gmail.picono435.picojobs.sponge.command.SpongeJobsCommand;
import com.gmail.picono435.picojobs.sponge.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.sponge.platform.*;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Logger;
import org.bstats.MetricsBase;
import org.bstats.sponge.Metrics;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.placeholder.PlaceholderParsers;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.permission.PermissionDescription;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.plugin.PluginCandidate;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.JVMPluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.plugin.builtin.jvm.locator.JVMPluginResource;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
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
    private final Metrics metrics;
    private PluginContainer pluginContainer;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFilePath;

    private Map<String, PlaceholderParser> placeholderParsers = new HashMap<>();

    @Inject
    public PicoJobsSponge(Logger logger, Game game, Metrics.Factory metricsFactory) {
        instance = this;
        this.logger = logger;
        this.game = game;
        this.metrics = metricsFactory.make(8553);
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        if(!event.plugin().metadata().id().equals("picojobs")) return;
        URL jarURL = null;
        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(logger.getName());
        try {
            if(event.plugin() instanceof JVMPluginContainer) {
                Field privateField = JVMPluginContainer.class.getDeclaredField("candidate");
                privateField.setAccessible(true);
                PluginCandidate<JVMPluginResource> candidate = (PluginCandidate<JVMPluginResource>) privateField.get(event.plugin());
                jarURL = candidate.resource().path().toUri().toURL();
            } else if(event.plugin().getClass().isAssignableFrom(Class.forName("org.spongepowered.forge.launch.plugin.PluginModContainer"))) {
                Object modInfo = event.plugin().getClass().getMethod("getModInfo").invoke(event.plugin());
                Object modFileInfo = modInfo.getClass().getMethod("getOwningFile").invoke(modInfo);
                Object modFile = modFileInfo.getClass().getMethod("getFile").invoke(modFileInfo);
                Path path = (Path) modFile.getClass().getMethod("getFilePath").invoke(modFile);
                jarURL = path.toUri().toURL();
            } else {
                slf4jLogger.error("This should never happen! Make sure to report this issue in https://github.com/Picono435/PicoJobs/issues. Maybe you are using a custom plugin loader?");
                return;
            }
        } catch(Exception ex) {
            slf4jLogger.error("There was an issue getting the JAR file of the plugin. Make sure to report this issue in https://github.com/Picono435/PicoJobs/issues with the following error.");
            ex.printStackTrace();
            return;
        }

        PicoJobsCommon.onLoad(
                event.plugin().metadata().version().toString(),
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
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        MetricsBase metricsBase;
        try {
            Field metricsBaseField = Metrics.class.getDeclaredField("metricsBase");
            metricsBaseField.setAccessible(true);
            metricsBase = (MetricsBase) metricsBaseField.get(this.metrics);
        } catch (Exception exception) {
            PicoJobsCommon.getLogger().error("Error while enabling bStats metrics. Enabling plugin without metrics.", exception);
            metricsBase = null;
        }
        PicoJobsCommon.onEnable(metricsBase);

        PermissionDescription.Builder builder = game.server().serviceProvider().provide(PermissionService.class).get().newDescriptionBuilder(this.pluginContainer);
        builder.id("picojobs.use.basic")
                .description(Component.text("Allow you to use the basics of the /jobs command"))
                .assign(PermissionDescription.ROLE_USER, true)
                .defaultValue(Tristate.TRUE)
                .register();

        builder.id("picojobs.admin")
                .description(Component.text("Allow you to use the /jobsadmin command"))
                .assign(PermissionDescription.ROLE_ADMIN, true)
                .assign(PermissionDescription.ROLE_USER, false)
                .defaultValue(Tristate.UNDEFINED)
                .register();
    }

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        Stream<PlaceholderParser> placeholders = PlaceholderParsers.registry().stream();
        placeholders.forEach((parser) -> {
            String placeholder = "%" + parser.key(RegistryTypes.PLACEHOLDER_PARSER).asString().replace("_", "_").replace(":", "_") + "%";
            if(placeholder.equals("%sponge_name%")) placeholder = "%player_name%";
            placeholderParsers.put(placeholder, parser);
        });
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

    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    public Map<String, PlaceholderParser> getPlaceholderParsers() {
        return placeholderParsers;
    }
}
