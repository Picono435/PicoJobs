package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.JobPlayer;

/**
 * Called when a player quits a job
 */
public class PlayerQuitJobEvent extends JobPlayerEvent {
    public PlayerQuitJobEvent(JobPlayer jobPlayer) {
        super(jobPlayer);
    }
}
