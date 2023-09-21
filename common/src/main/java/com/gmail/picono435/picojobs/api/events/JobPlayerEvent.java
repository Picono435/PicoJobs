package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.JobPlayer;

public class JobPlayerEvent {

    private final JobPlayer jobPlayer;
    private boolean cancelled;

    public JobPlayerEvent(JobPlayer jobPlayer) {
        this.jobPlayer = jobPlayer;
    }


    public JobPlayer getJobPlayer() {
        return this.jobPlayer;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public interface Listener<T extends JobPlayerEvent> {
        void onEvent(T event);
    }
}
