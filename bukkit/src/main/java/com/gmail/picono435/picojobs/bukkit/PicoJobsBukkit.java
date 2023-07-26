package com.gmail.picono435.picojobs.bukkit;

import com.gmail.picono435.picojobs.bukkit.platform.*;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitLoggerAdapter;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class PicoJobsBukkit extends JavaPlugin {

    private static PicoJobsBukkit instance;

    @Override
    public void onLoad() {
        instance = this;
        PicoJobsCommon.onLoad(
                getDescription().getVersion(),
                Platform.BUKKIT,
                new BukkitLoggerAdapter((PluginLogger) PicoJobsBukkit.getInstance().getLogger()),
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

    @Override
    public void onDisable() {
        PicoJobsCommon.onDisable();
    }

    public static PicoJobsBukkit getInstance() {
        return instance;
    }
}
