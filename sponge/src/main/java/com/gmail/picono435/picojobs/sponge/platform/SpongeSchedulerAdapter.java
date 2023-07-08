package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.scheduler.SchedulerAdapter;
import com.gmail.picono435.picojobs.common.platform.scheduler.SchedulerTask;
import com.google.common.base.Suppliers;
import org.spongepowered.api.Game;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scheduler.TaskExecutorService;
import org.spongepowered.plugin.PluginContainer;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpongeSchedulerAdapter implements SchedulerAdapter {

    private final Game game;
    private final PluginContainer pluginContainer;

    private final Scheduler asyncScheduler;
    private final Supplier<TaskExecutorService> sync;
    private final TaskExecutorService async;

    private final Set<ScheduledTask> tasks = Collections.newSetFromMap(new WeakHashMap<>());

    public SpongeSchedulerAdapter(Game game, PluginContainer pluginContainer) {
        this.game = game;
        this.pluginContainer = pluginContainer;

        this.asyncScheduler = game.asyncScheduler();
        this.async = this.asyncScheduler.executor(pluginContainer);
        this.sync = Suppliers.memoize(() -> getSyncScheduler().executor(this.pluginContainer));
    }

    @Override
    public Executor async() {
        return this.async;
    }

    @Override
    public Executor sync() {
        return this.sync.get();
    }

    public Scheduler getSyncScheduler() {
        return this.game.server().scheduler();
    }

    private SchedulerTask submitAsyncTask(Runnable runnable, Consumer<Task.Builder> config) {
        Task.Builder builder = Task.builder();
        config.accept(builder);

        Task task = builder
                .execute(runnable)
                .plugin(this.pluginContainer)
                .build();

        ScheduledTask scheduledTask = this.asyncScheduler.submit(task);
        this.tasks.add(scheduledTask);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask asyncLater(Runnable task, long delay, TimeUnit unit) {
        return submitAsyncTask(task, builder -> builder.delay(delay, unit));
    }

    @Override
    public SchedulerTask asyncRepeating(Runnable task, long interval, TimeUnit unit) {
        return submitAsyncTask(task, builder -> builder.delay(interval, unit).interval(interval, unit));
    }

    @Override
    public void shutdownScheduler() {
        for(ScheduledTask task : this.tasks) {
            task.cancel();
        }
    }

    @Override
    public void shutdownExecutor() {
        // do nothing
    }
}