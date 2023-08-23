package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.*;
import com.gmail.picono435.picojobs.common.platform.scheduler.SchedulerAdapter;
import com.gmail.picono435.picojobs.common.platform.WhitelistConverter;
import io.github.slimjar.app.builder.ApplicationBuilder;
import io.github.slimjar.app.builder.IsolatedApplicationBuilder;
import io.github.slimjar.app.builder.IsolationConfiguration;
import io.github.slimjar.app.module.TemporaryModuleExtractor;
import io.github.slimjar.injector.DependencyInjector;
import io.github.slimjar.injector.DependencyInjectorFactory;
import io.github.slimjar.injector.agent.ByteBuddyInstrumentationFactory;
import io.github.slimjar.injector.helper.InjectionHelperFactory;
import io.github.slimjar.injector.loader.InjectableClassLoader;
import io.github.slimjar.injector.loader.InjectableFactory;
import io.github.slimjar.injector.loader.InstrumentationInjectable;
import io.github.slimjar.resolver.data.Repository;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.slf4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Collections;

public class PicoJobsCommon {
    // Platform specific features
    private static String version;
    private static Platform platform;
    private static Logger logger;
    private static File configDir;
    private static File updateDir;
    private static SchedulerAdapter schedulerAdapter;
    private static PlatformAdapter platformAdapter;
    private static ColorConverter colorConverter;
    private static PlaceholderTranslator placeholderTranslator;
    private static WhitelistConverter whitelistConverter;
    private static SoftwareHooker softwareHooker;

    // Non-Platform specific
    private static PicoJobsMain mainInstance;
    private static FileManager fileManager;

    public static void onLoad(String version, Platform platform, Logger logger, File configDir, File updateDir, SchedulerAdapter schedulerAdapter, PlatformAdapter platformAdapter, ColorConverter colorConverter, PlaceholderTranslator placeholderTranslator, WhitelistConverter whitelistConverter, SoftwareHooker softwareHooker) {
        onLoad(version, platform, logger, configDir, updateDir, schedulerAdapter, platformAdapter, colorConverter, placeholderTranslator, whitelistConverter, softwareHooker, null, null);
    }

    public static void onLoad(String version, Platform platform, Logger logger, File configDir, File updateDir, SchedulerAdapter schedulerAdapter, PlatformAdapter platformAdapter, ColorConverter colorConverter, PlaceholderTranslator placeholderTranslator, WhitelistConverter whitelistConverter, SoftwareHooker softwareHooker, URL jarURL) {
        onLoad(version, platform, logger, configDir, updateDir, schedulerAdapter, platformAdapter, colorConverter, placeholderTranslator, whitelistConverter, softwareHooker, jarURL, null);
    }

    public static void onLoad(String version, Platform platform, Logger logger, File configDir, File updateDir, SchedulerAdapter schedulerAdapter, PlatformAdapter platformAdapter, ColorConverter colorConverter, PlaceholderTranslator placeholderTranslator, WhitelistConverter whitelistConverter, SoftwareHooker softwareHooker, URL jarURL, DependencyInjectorFactory dependencyInjectorFactory) {
        if(PicoJobsCommon.version != null) return;
        PicoJobsCommon.version = version;
        PicoJobsCommon.platform = platform;
        PicoJobsCommon.logger = logger;
        PicoJobsCommon.configDir = configDir;
        PicoJobsCommon.updateDir = updateDir;
        PicoJobsCommon.schedulerAdapter = schedulerAdapter;
        PicoJobsCommon.platformAdapter = platformAdapter;
        PicoJobsCommon.colorConverter = colorConverter;
        PicoJobsCommon.placeholderTranslator = placeholderTranslator;
        PicoJobsCommon.whitelistConverter = whitelistConverter;
        PicoJobsCommon.softwareHooker = softwareHooker;

        PicoJobsCommon.getLogger().info("Loading dependencies, this might take some minutes when ran for the first time...");
        try {
            ApplicationBuilder applicationBuilder;
            if(platform == Platform.FORGE) {
                applicationBuilder = ApplicationBuilder.injecting("PicoJobs",
                        new InjectableClassLoader(new URL[]{new TemporaryModuleExtractor().extractModule(InstrumentationInjectable.class.getClassLoader().getResource(ByteBuddyInstrumentationFactory.AGENT_JAR), "loader-agent")}, PicoJobsCommon.class.getClassLoader()) {
                            @Override
                            public void inject(URL url) {
                                super.inject(url);
                            }
                        });
            } else {
                applicationBuilder = ApplicationBuilder.appending("PicoJobs");
            }
            applicationBuilder.mirrorSelector((collection, collection1) -> collection)
                    .downloadDirectoryPath(PicoJobsCommon.getConfigDir().toPath().resolve("libraries"));
            if(jarURL != null) {
                applicationBuilder.jarURL(jarURL);
            }
            if(dependencyInjectorFactory != null) {
                applicationBuilder.injectorFactory(dependencyInjectorFactory);
            }
            applicationBuilder.build();
            PicoJobsCommon.getLogger().info("All dependencies were loaded sucessfully.");
        } catch (Exception ex) {
            PicoJobsCommon.getLogger().error("An error occuried while loading SLIMJAR, go into https://github.com/Picono435/PicoJobs/wiki/Common-Issues#dependency-loading-issues with the following error:");
            ex.printStackTrace();
        }

        fileManager = new FileManager();
        fileManager.init();
    }

    public static void onEnable() {
        PicoJobsCommon.getLogger().info("Plugin created by: Picono435#2011. Thank you for using it");
        mainInstance = new PicoJobsMain();
        mainInstance.init();
    }

    public static void onDisable() {
        PicoJobsCommon.getLogger().info("Disconnecting connection to storage...");
        mainInstance.jobs.clear();

        PicoJobsAPI.getStorageManager().destroyStorageFactory();

        /*if(wasUpdated && PicoJobsAPI.getStorageManager().getStorageFactory() instanceof H2Storage) {
            try {
                Script.main("-url jdbc:h2:$f -script $f.zip -options compression zip".replace("$f", PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString()).split(" "));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        PicoJobsCommon.getLogger().info("The plugin was succefully disabled.");
    }

    /**
     * Same as having serverVersion &gt;= specifiedVersion
     * Example: 1.18.1 &gt;= 1.13.2
     *
     * @param version
     * @return
     */
    public static boolean isMoreThan(String version) {
        DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion(version);
        DefaultArtifactVersion serverVersionArt = new DefaultArtifactVersion(getPlatformAdapter().getMinecraftVersion());
        if(serverVersionArt.compareTo(legacyVersion) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Same as having serverVersion &lt;= specifiedVersion
     * Example: 1.18.1 &lt;= 1.13.2
     *
     * @param version
     * @return
     */
    public static boolean isLessThan(String version) {
        DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion(version);
        DefaultArtifactVersion serverVersionArt = new DefaultArtifactVersion(getPlatformAdapter().getMinecraftVersion());
        if(serverVersionArt.compareTo(legacyVersion) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getVersion() {
        return version;
    }

    public static Platform getPlatform() {
        return platform;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static SchedulerAdapter getSchedulerAdapter() {
        return schedulerAdapter;
    }

    public static PlatformAdapter getPlatformAdapter() {
        return platformAdapter;
    }

    public static ColorConverter getColorConverter() {
        return colorConverter;
    }

    public static PlaceholderTranslator getPlaceholderTranslator() {
        return placeholderTranslator;
    }

    public static WhitelistConverter getWhitelistConverter() {
        return whitelistConverter;
    }

    public static SoftwareHooker getSoftwareHooker() {
        return softwareHooker;
    }

    public static PicoJobsMain getMainInstance() {
        return mainInstance;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
