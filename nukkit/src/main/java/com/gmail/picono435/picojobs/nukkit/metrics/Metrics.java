package com.gmail.picono435.picojobs.nukkit.metrics;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import org.apache.commons.io.FileUtils;
import org.bstats.MetricsBase;
import org.bstats.charts.CustomChart;
import org.bstats.json.JsonObjectBuilder;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Metrics {

    private final PicoJobsNukkit plugin;
    private final MetricsBase metricsBase;

    /**
     * Creates a new Metrics instance.
     *
     * @param plugin Your plugin instance.
     * @param serviceId The id of the service.
     *                  It can be found at <a href="https://bstats.org/what-is-my-plugin-id">What is my plugin id?</a>
     */
    public Metrics(PicoJobsNukkit plugin, int serviceId) throws IOException {
        this.plugin = plugin;

        // Get the config file
        File bStatsFolder = new File(PicoJobsCommon.getConfigDir().getParentFile(), "bStats");
        File configFile = new File(bStatsFolder, "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            URL inputUrl = getClass().getResource("/bStats/bstats.yml");
            FileUtils.copyURLToFile(inputUrl, configFile);
        }

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(configFile.toPath())
                .build();

        CommentedConfigurationNode config = loader.load();

        if (config.node("serverUuid").empty()) {
            config.node("serverUuid").set(UUID.randomUUID().toString());
            try {
                loader.save(config);
            } catch (IOException ignored) { }
        }

        // Load the data
        boolean enabled = config.node("enabled").getBoolean(true);
        String serverUUID = config.node("serverUuid").getString();
        boolean logErrors = config.node("logFailedRequests").getBoolean(false);
        boolean logSentData = config.node("logSentData").getBoolean(false);
        boolean logResponseStatusText = config.node("logResponseStatusText").getBoolean(false);

        // We will be using bukkit as the main plugin for now
        metricsBase = new MetricsBase(
                "bukkit",
                serverUUID,
                serviceId,
                enabled,
                this::appendPlatformData,
                this::appendServiceData,
                submitDataTask -> PicoJobsCommon.getSchedulerAdapter().executeSync(submitDataTask),
                () -> true,
                (message, error) -> PicoJobsCommon.getLogger().warn(message, error),
                (message) -> PicoJobsCommon.getLogger().info(message),
                logErrors,
                logSentData,
                logResponseStatusText
        );
    }

    /**
     * Shuts down the underlying scheduler service.
     */
    public void shutdown() {
        metricsBase.shutdown();
    }

    /**
     * Adds a custom chart.
     *
     * @param chart The chart to add.
     */
    public void addCustomChart(CustomChart chart) {
        metricsBase.addCustomChart(chart);
    }

    private void appendPlatformData(JsonObjectBuilder builder) {
        builder.appendField("playerAmount", plugin.getServer().getOnlinePlayers().size());
        //builder.appendField("onlineMode", plugin.getServer().onlineMode() ? 1 : 0);
        builder.appendField("onlineMode", 1);
        builder.appendField("bukkitVersion", PicoJobsCommon.getPlatformAdapter().getPlatformVersion());
        builder.appendField("bukkitName", PicoJobsCommon.getPlatform().name().substring(0, 1).toUpperCase() + PicoJobsCommon.getPlatform().name().substring(1));

        builder.appendField("javaVersion", System.getProperty("java.version"));
        builder.appendField("osName", System.getProperty("os.name"));
        builder.appendField("osArch", System.getProperty("os.arch"));
        builder.appendField("osVersion", System.getProperty("os.version"));
        builder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
    }

    private void appendServiceData(JsonObjectBuilder builder) {
        builder.appendField("pluginVersion", PicoJobsCommon.getVersion());
    }

    public MetricsBase getMetricsBase() {
        return metricsBase;
    }
}
