package com.gmail.picono435.picojobs.nukkit;

import cn.nukkit.plugin.PluginBase;
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
                Platform.BUKKIT,
                LoggerFactory.getLogger(PicoJobsNukkit.class),
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
        PicoJobsCommon.onEnable();
    }

    public static PicoJobsNukkit getInstance() {
        return instance;
    }
}
