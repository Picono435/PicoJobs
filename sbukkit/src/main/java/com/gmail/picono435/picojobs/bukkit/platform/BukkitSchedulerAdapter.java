package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.common.platform.scheduler.AbstractJavaScheduler;
import com.gmail.picono435.picojobs.common.platform.scheduler.SchedulerAdapter;
import org.bukkit.Bukkit;

import java.util.concurrent.Executor;

public class BukkitSchedulerAdapter extends AbstractJavaScheduler implements SchedulerAdapter {
    private final Executor sync;

    public BukkitSchedulerAdapter() {
        this.sync = r -> Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PicoJobsBukkit.getInstance(), r);
    }

    @Override
    public Executor sync() {
        return this.sync;
    }

}