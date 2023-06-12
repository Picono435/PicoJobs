package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.ColorConverter;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;

import java.io.File;
import java.util.logging.Logger;

public class PicoJobsCommon {
    // Platform specific features
    private static String version;
    private static Platform platform;
    private static Logger logger;
    private static File configDir;
    private static ColorConverter colorConverter;
    private static PlaceholderTranslator placeholderTranslator;
    private static SoftwareHooker softwareHooker;

    // Non-Platform specific
    private static PicoJobsMain mainInstance;
    private static FileManager fileManager;


    public static void onLoad(String version, Platform platform, Logger logger, File configDir, ColorConverter colorConverter, PlaceholderTranslator placeholderTranslator, SoftwareHooker softwareHooker) {
        if(PicoJobsCommon.version != null) return;
        PicoJobsCommon.version = version;
        PicoJobsCommon.platform = platform;
        PicoJobsCommon.logger = logger;
        PicoJobsCommon.configDir = configDir;
        PicoJobsCommon.colorConverter = colorConverter;
        PicoJobsCommon.placeholderTranslator = placeholderTranslator;
        PicoJobsCommon.softwareHooker = softwareHooker;
    }

    public static void onEnable() {
        PicoJobsCommon.getLogger().info("Plugin created by: Picono435#2011. Thank you for using it");
        fileManager = new FileManager();
        fileManager.init();
        mainInstance = new PicoJobsMain();
        mainInstance.init();
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

    public static ColorConverter getColorConverter() {
        return colorConverter;
    }

    public static PlaceholderTranslator getPlaceholderTranslator() {
        return placeholderTranslator;
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
