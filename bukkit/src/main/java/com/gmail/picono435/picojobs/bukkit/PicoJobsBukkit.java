package com.gmail.picono435.picojobs.bukkit;

import com.gmail.picono435.picojobs.bukkit.platform.*;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

public class PicoJobsBukkit extends JavaPlugin {

    private static PicoJobsBukkit instance;

    @Override
    public void onLoad() {
        instance = this;

        PicoJobsCommon.onLoad(
                getDescription().getVersion(),
                Platform.BUKKIT,
                LoggerFactory.getLogger(getLogger().getName()),
                getDataFolder(),
                Bukkit.getUpdateFolderFile(),
                new BukkitSchedulerAdapter(),
                new BukkitPlatformAdapter(),
                new BukkitColorConverter(),
                new BukkitPlaceholderTranslator(),
                new BukkitWhitelistConverter(),
                new BukkitSoftwareHooker()
        );
    }

    @Override
    public void onEnable() {
        PicoJobsCommon.onEnable();
    }

    public static PicoJobsBukkit getInstance() {
        return instance;
    }
}
