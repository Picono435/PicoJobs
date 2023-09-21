package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;

/**
 * Called when a player enters a job
 */
public class PlayerEmployedEvent extends JobPlayerEvent {
    private Job job;

    public PlayerEmployedEvent(JobPlayer jobPlayer, Job job) {
        super(jobPlayer);
    }

    /**
     * Returns the job that the player has been employed into
     *
     * @return new player job
     */
    public Job getJob() {
        return job;
    }
}
