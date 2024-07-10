package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.common.platform.scheduler.AbstractJavaScheduler;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;

import java.util.concurrent.Executor;

public class ModSchedulerAdapter extends AbstractJavaScheduler {
    private Executor sync;

    public ModSchedulerAdapter() {

    }

    public void init() {
        this.sync = r -> PicoJobsMod.getServer().orElseThrow(() -> new IllegalStateException("Server not ready")).executeBlocking(r);
    }

    @Override
    public Executor sync() {
        return this.sync;
    }
}