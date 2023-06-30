package com.gmail.picono435.picojobs.nukkit.platform;

import com.gmail.picono435.picojobs.common.platform.scheduler.AbstractJavaScheduler;
import com.gmail.picono435.picojobs.common.platform.scheduler.SchedulerAdapter;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.concurrent.Executor;

public class NukkitSchedulerAdapter extends AbstractJavaScheduler implements SchedulerAdapter {
    private final Executor sync;

    public NukkitSchedulerAdapter() {
        this.sync = r -> PicoJobsNukkit.getInstance().getServer().getScheduler().scheduleTask(PicoJobsNukkit.getInstance(), r, false);
    }

    @Override
    public Executor sync() {
        return this.sync;
    }

}