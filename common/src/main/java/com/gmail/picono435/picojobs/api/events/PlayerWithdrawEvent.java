package com.gmail.picono435.picojobs.api.events;

import com.gmail.picono435.picojobs.api.JobPlayer;

/**
 * Called when a player withdraws their salary
 */
public class PlayerWithdrawEvent extends JobPlayerEvent {
    private double salary;

    public PlayerWithdrawEvent(JobPlayer jobPlayer, double salary) {
        super(jobPlayer);
        this.salary = salary;
    }

    /**
     * Returns the amount of salary that is being withdrawn
     *
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the new amount of salary to be withdrawn instead of the default one
     *
     * @param salary the new salary amount
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
