package com.gmail.picono435.picojobs.bukkit;

import com.gmail.picono435.picojobs.bukkit.platform.*;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitLoggerAdapter;
import com.gmail.picono435.picojobs.bukkit.utils.NamespacedLegacyUtils;
import com.gmail.picono435.picojobs.bukkit.utils.NamespacedRegistryUtils;
import com.gmail.picono435.picojobs.bukkit.utils.NamespacedUtils;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import org.bstats.MetricsBase;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class PicoJobsBukkit extends JavaPlugin {

    private static PicoJobsBukkit instance;
    private static NamespacedUtils namespacedUtils;

    @Override
    public void onLoad() {
        instance = this;
        PicoJobsCommon.onLoad(
                getDescription().getVersion(),
                Platform.BUKKIT,
                new BukkitLoggerAdapter(PicoJobsBukkit.getInstance().getLogger()),
                getDataFolder(),
                Bukkit.getUpdateFolderFile(),
                new BukkitSchedulerAdapter(),
                new BukkitPlatformAdapter(),
                new BukkitColorConverter(),
                new BukkitPlaceholderTranslator(),
                new BukkitWhitelistConverter(),
                new BukkitSoftwareHooker(),
                new BukkitRegistryCollector()
        );

        if(PicoJobsCommon.isMoreThan("1.20.1")) { // Why 1.20.1? Because it's when Registry#stream was implemented
            namespacedUtils = new NamespacedRegistryUtils();
        } else {
            namespacedUtils = new NamespacedLegacyUtils();
        }
    }

    @Override
    public void onEnable() {
        MetricsBase metricsBase;
        try {
            Field metricsBaseField = Metrics.class.getDeclaredField("metricsBase");
            metricsBaseField.setAccessible(true);
            metricsBase = (MetricsBase) metricsBaseField.get(new Metrics(this, 8553));
        } catch (Exception exception) {
            PicoJobsCommon.getLogger().error("Error while enabling bStats metrics. Enabling plugin without metrics.", exception);
            metricsBase = null;
        }
        PicoJobsCommon.onEnable(metricsBase);
    }

    @Override
    public void onDisable() {
        PicoJobsCommon.onDisable();
    }

    public static PicoJobsBukkit getInstance() {
        return instance;
    }

    public static NamespacedUtils getNamespacedUtils() {
        return namespacedUtils;
    }
}
