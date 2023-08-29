package com.gmail.picono435.picojobs.nukkit;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.nukkit.metrics.Metrics;
import com.gmail.picono435.picojobs.nukkit.platform.*;
import com.gmail.picono435.picojobs.nukkit.platform.logging.NukkitLoggerAdapter;
import me.iwareq.fakeinventories.FakeInventories;
import org.bstats.MetricsBase;

import java.io.IOException;

public class PicoJobsNukkit extends PluginBase {

    private static PicoJobsNukkit instance;
    private NukkitLoggerAdapter loggerAdapter;

    @Override
    public void onLoad() {
        instance = this;
        loggerAdapter = new NukkitLoggerAdapter(getLogger());

        PicoJobsCommon.onLoad(
                getDescription().getVersion(),
                Platform.NUKKIT,
                loggerAdapter,
                getDataFolder(),
                null,
                new NukkitSchedulerAdapter(),
                new NukkitPlatformAdapter(),
                new NukkitColorConverter(),
                new NukkitPlaceholderTranslator(),
                new NukkitWhitelistConverter(),
                new NukkitSoftwareHooker()
        );
    }

    @Override
    public void onEnable() {
        MetricsBase metricsBase;
        try {
            metricsBase = new Metrics(this, 8553).getMetricsBase();
        } catch (IOException exception) {
            PicoJobsCommon.getLogger().error("Error while enabling bStats metrics. Enabling plugin without metrics.", exception);
            metricsBase = null;
        }
        PicoJobsCommon.onEnable(metricsBase);
        FakeInventories fakeInventories = new FakeInventories();
        fakeInventories.init(this.getPluginLoader(), this.getServer(), this.getDescription(), this.getDataFolder(), this.getFile());
        fakeInventories.setEnabled(true);
        fakeInventories.onEnable();
        PicoJobsCommon.getLogger().info("PicoJobs uses the plugin FakeInventories to create inventories in nukkit. This plugin comes embed with PicoJobs, current version is 1.1.4.");
    }

    @Override
    public void onDisable() {
        PicoJobsCommon.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "jobs": {
                PicoJobsCommon.getMainInstance().getJobsCommand().onCommand(command.getName(), args, new NukkitSender(sender));
                break;
            }
            case "jobsadmin": {
                PicoJobsCommon.getMainInstance().getJobsAdminCommand().onCommand(command.getName(), args, new NukkitSender(sender));
                break;
            }
        }
        return true;
    }

    public static PicoJobsNukkit getInstance() {
        return instance;
    }

    public NukkitLoggerAdapter getLoggerAdapter() {
        return loggerAdapter;
    }
}
