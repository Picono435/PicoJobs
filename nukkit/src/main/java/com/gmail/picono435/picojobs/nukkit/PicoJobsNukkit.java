package com.gmail.picono435.picojobs.nukkit;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.MainLogger;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.nukkit.platform.*;
import com.gmail.picono435.picojobs.nukkit.platform.logging.NukkitLoggerAdapter;
import org.slf4j.LoggerFactory;

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

        if(getServer().getPluginManager().getPlugin("FakeInventories") == null || getServer().getPluginManager().getPlugin("FakeInventories").isDisabled()) {
            PicoJobsCommon.getLogger().warn("PicoJobs requires the plugin FakeInventories to create inventories in nukkit. PicoJobs will automatically disable inventories because the plugin was not found.");
            PicoJobsAPI.getSettingsManager().setCommandAction(4);
        }
    }

    @Override
    public void onEnable() {
        PicoJobsCommon.onEnable();
    }

    public static PicoJobsNukkit getInstance() {
        return instance;
    }

    public NukkitLoggerAdapter getLoggerAdapter() {
        return loggerAdapter;
    }
}
