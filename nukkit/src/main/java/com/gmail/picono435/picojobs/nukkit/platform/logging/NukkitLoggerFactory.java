package com.gmail.picono435.picojobs.nukkit.platform.logging;

import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class NukkitLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        System.out.println("YO: " + name);
        if(!name.equals("PicoJobs")) return null;
        return new NukkitLoggerAdapter(PicoJobsNukkit.getInstance().getLogger());
    }
}
