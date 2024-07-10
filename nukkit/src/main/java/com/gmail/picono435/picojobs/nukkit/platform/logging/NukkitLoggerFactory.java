package com.gmail.picono435.picojobs.nukkit.platform.logging;

import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class NukkitLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return PicoJobsNukkit.getInstance().getLoggerAdapter();
    }
}
