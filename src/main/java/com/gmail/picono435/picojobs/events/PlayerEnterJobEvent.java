package com.gmail.picono435.picojobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;

public final class PlayerEnterJobEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;
    
    
    private JobPlayer jobPlayer;
    private Player player;
    private Job job;
    
    public PlayerEnterJobEvent(JobPlayer jobPlayer, Player player, Job job) {
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

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
