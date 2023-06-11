package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.ColorConverter;
import com.gmail.picono435.picojobs.common.platform.PlaceholderTranslator;
import com.gmail.picono435.picojobs.common.platform.Platform;

import java.io.File;
import java.util.logging.Logger;

public class PicoJobsCommon {
    // Platform specific features
    private static Platform platform;
    private static Logger logger;
    private static File configDir;
    private static ColorConverter colorConverter;
    private static PlaceholderTranslator placeholderTranslator;

    // Non-Platform specific
    private static FileManager fileManager;


    public static void init(Platform platform, Logger logger, File configDir, ColorConverter colorConverter, PlaceholderTranslator placeholderTranslator) {
        if(PicoJobsCommon.platform != null) return;
        PicoJobsCommon.platform = platform;
        PicoJobsCommon.logger = logger;
        PicoJobsCommon.configDir = configDir;
        PicoJobsCommon.colorConverter = colorConverter;
        PicoJobsCommon.placeholderTranslator = placeholderTranslator;

        fileManager = new FileManager();
        fileManager.init();
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
}
