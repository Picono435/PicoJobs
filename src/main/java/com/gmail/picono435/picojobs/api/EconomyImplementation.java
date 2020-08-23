package com.gmail.picono435.picojobs.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class EconomyImplementation {

	protected Plugin requiredPlugin;
	
	  /**
	   * Returns a upper case name of economy
	   *
	   * @return economy name
	   */
	  public abstract String getName();

	  /**
	   * Gets the money balance of specified player
	   *
	   * @param player Player to check balance
	   * @return money balance
	   */
	  public abstract double getBalance(Player player);

	  /**
	   * Adds the specified money amount to player's balance
	   *
	   * @param player Player whose balance shall be modified
	   * @param amount Money amount
	   */
	  public abstract void deposit(Player player, double amount);

	  /**
	   * Removes the specified money amount from player's balance
	   *
	   * @param player Player whose balance shall be modified
	   * @param amount Money amount
	   */
	  public abstract void withdraw(Player player, double amount);

	  /**
	   * Checks whether player's balance is equal or higher than the specified money amount
	   *
	   * @param player Player whose balance shall be checked
	   * @param amount Amount required from player
	   * @return whether player has enough money
	   */
	  public boolean has(Player player, double amount) {
	    return getBalance(player) >= amount;
	  }
	  
	  /**
	   * Gets the required plugin in order to this implementation be enabled
	   *
	   * @return required plugin
	   */
	  public Plugin getRequiredPlugin() {
	    return requiredPlugin;
	  }
}
