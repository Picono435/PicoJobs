package com.gmail.picono435.picojobs.api;
import com.gmail.picono435.picojobs.api.field.RequiredField;

import java.util.UUID;

public abstract class EconomyImplementation {

	protected String requiredPlugin = "PicoJobs";

	/**
	 * Returns an upper case name of the implementation
	 *
	 * @return economy name
	 */
	public abstract String getName();

	/**
	 * This method is automatically called during the implementation registry
	 * proccess if the required plugin for this implementation is found and enabled. <br>
	 * <br>
	 * This should be where you set up the required field for your implementation.
	 *
	 */
	public void onRegister() {}

	/**
	 * Gets the money balance of specified player
	 *
	 * @param player Player to check balance
	 * @return money balance
	 */
	public abstract double getBalance(UUID player);

	/**
	 * Adds the specified money amount to player's balance
	 *
	 * @param player Player whose balance shall be modified
	 * @param amount Money amount
	 */
	public abstract void deposit(UUID player, double amount);

	/**
	 * Removes the specified money amount from player's balance
	 *
	 * @param player Player whose balance shall be modified
	 * @param amount Money amount
	 */
	public abstract void withdraw(UUID player, double amount);

	/**
	 * Checks whether player's balance is equal or higher than the specified money amount
	 *
	 * @param player Player whose balance shall be checked
	 * @param amount Amount required from player
	 * @return whether player has enough money
	 */
	public boolean has(UUID player, double amount) {
		return getBalance(player) >= amount;
	}

	/**
	 * Gets the required plugin in order to this implementation be enabled
	 *
	 * @return required plugin
	 */
	public String getRequiredPlugin() {
		return requiredPlugin;
	}

	/**
	 * Gets the required field in order to this implementation work correctly
	 * Required field is currently only used in PicoJobs editor but should be set for a better user experience
	 *
	 * @return required field
	 */
	public RequiredField<?, ?> getRequiredField() {
        return null;
    };

}