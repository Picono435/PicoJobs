package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.JobPlayer;

/**
 * Called when a player starts working in a job
 */
public class PlayerStartedWorkingEvent extends JobPlayerEvent {
    public PlayerStartedWorkingEvent(JobPlayer jobPlayer) {
        super(jobPlayer);
    }
}
