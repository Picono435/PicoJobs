package com.gmail.picono435.picojobs.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class EconomyImplementation {

	protected Plugin requiredPlugin;
	/**
	 * Required field is currently only used in PicoJobs editor but should be set for a better user experience
	 */
	protected RequiredField requiredField;
	
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

	/**
	 * Gets the required field in order to this implementation work correctly
	 * Required field is currently only used in PicoJobs editor but should be set for a better user experience
	 *
	 * @return required field
	 */
	public RequiredField getRequiredField() {
		return requiredField;
	}

	public class RequiredField {

		private String name;
		private RequiredFieldType type;

		public RequiredField(String name, RequiredFieldType type) {
			this.name = name;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public RequiredFieldType getType() {
			return type;
		}
	}


	public enum RequiredFieldType {
		STRING_LIST,
		STRING,
		INTEGER,
		DOUBLE,
		BOOLEAN;

		public JsonElement getJsonElement(Object object) {
			switch (this) {
				case STRING_LIST:
					JsonArray jsonArray = new JsonArray();
					for(String s : (List<String>) object) jsonArray.add(s);
					return jsonArray;
				case STRING:
					return new JsonPrimitive((String) object);
				case INTEGER:
					return new JsonPrimitive((int) object);
				case DOUBLE:
					return new JsonPrimitive((double) object);
				case BOOLEAN:
					return new JsonPrimitive((boolean) object);
			}
			return null;
		}
	}
}
