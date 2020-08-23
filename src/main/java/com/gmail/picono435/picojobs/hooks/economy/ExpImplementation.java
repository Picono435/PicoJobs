package com.gmail.picono435.picojobs.hooks.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.api.EconomyImplementation;

public class ExpImplementation extends EconomyImplementation {
	
	public ExpImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PicoJobs");
	}
	
	@Override
	public String getName() {
		return "EXP";
	}

	@Override
	public double getBalance(Player player) {
		return player.getLevel();
	}

	@Override
	public void deposit(Player player, double amount) {
		player.setLevel(player.getLevel() + (int)amount);
	}

	@Override
	public void withdraw(Player player, double amount) {
		player.setLevel(player.getLevel() - (int)amount);
	}

}
