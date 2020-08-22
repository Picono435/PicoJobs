package com.gmail.picono435.picojobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;

public final class PlayerLeaveJobEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private JobPlayer jobPlayer;
    private Player player;
    private Job oldJob;
    
    public PlayerLeaveJobEvent(JobPlayer jobPlayer, Player player, Job oldJob) {
        this.jobPlayer = jobPlayer;
        this.player = player;
        this.oldJob = oldJob;
    }

    /**
     * Gets the job player in this event
     * 
     * @return the job player
     */
    public JobPlayer getJobPlayer() {
        return jobPlayer;
    }
    
    /**
     * Gets the player in this event
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
   
    /**
     * Gets the old job of the player
     * 
     * @return the old job
     */
    public Job getOldJob() {
    	return oldJob;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
