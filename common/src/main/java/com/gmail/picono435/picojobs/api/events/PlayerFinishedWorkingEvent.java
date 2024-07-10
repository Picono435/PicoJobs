package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.JobPlayer;

/**
 * Called when a player finishes a work in a job
 */
public class PlayerFinishedWorkingEvent extends JobPlayerEvent {
    private int oldLevel;
    private int newLevel;

    public PlayerFinishedWorkingEvent(JobPlayer jobPlayer, int oldLevel) {
        super(jobPlayer);
        this.oldLevel = oldLevel;
        this.newLevel = oldLevel + 1;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }
}
