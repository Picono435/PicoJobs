package com.gmail.picono435.picojobs.events;

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

    public JobPlayer getJobPlayer() {
        return jobPlayer;
    }
    
    public Player getPlayer() {
        return player;
    }
   
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
