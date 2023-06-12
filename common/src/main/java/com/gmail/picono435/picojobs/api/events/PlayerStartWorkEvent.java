package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.picono435.picojobs.api.JobPlayer;

public final class PlayerStartWorkEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private JobPlayer jobPlayer;
    private Player player;
    private Job job;
    
    public PlayerStartWorkEvent(JobPlayer jobPlayer, Player player, Job job) {
        this.jobPlayer = jobPlayer;
        this.player = player;
        this.job = job;
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
     * Gets the job of the player in this event
     * 
     * @return the job
     */
    public Job getJob() {
    	return job;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
