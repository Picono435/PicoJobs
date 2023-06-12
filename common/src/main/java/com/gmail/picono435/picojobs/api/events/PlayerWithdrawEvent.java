package com.gmail.picono435.picojobs.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.picono435.picojobs.api.JobPlayer;

public final class PlayerWithdrawEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private JobPlayer jobPlayer;
    private Player player;
    private double salary;
    
    public PlayerWithdrawEvent(JobPlayer jobPlayer, Player player, double salary) {
        this.jobPlayer = jobPlayer;
        this.player = player;
        this.salary = salary;
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
     * Gets the salary took in this event
     * 
     * @return the salary took
     */
    public double getSalary() {
    	return salary;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
