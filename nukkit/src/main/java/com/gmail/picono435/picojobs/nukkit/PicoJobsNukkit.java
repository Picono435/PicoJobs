package com.gmail.picono435.picojobs.nukkit;

import cn.nukkit.plugin.PluginBase;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.nukkit.platform.*;
import org.slf4j.LoggerFactory;

public class PicoJobsNukkit extends PluginBase {

    private static PicoJobsNukkit instance;

    @Override
    public void onLoad() {
        instance = this;

        PicoJobsCommon.onLoad(
                getDescription().getVersion(),
                Platform.NUKKIT,
                LoggerFactory.getLogger(this.getName()),
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
}
