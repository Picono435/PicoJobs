package com.gmail.picono435.picojobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.picono435.picojobs.api.Job;
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

    public JobPlayer getJobPlayer() {
        return jobPlayer;
    }
    
    public Player getPlayer() {
        return player;
    }
   
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
