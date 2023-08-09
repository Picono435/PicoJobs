package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import org.bukkit.Bukkit;

import com.gmail.picono435.picojobs.api.EconomyImplementation;

import java.util.UUID;

public class ExpImplementation extends EconomyImplementation {
	
	public ExpImplementation() {
		this.requiredPlugin = "PicoJobs";
	}
	
	@Override
	public String getName() {
		return "EXP";
	}

	@Override
	public double getBalance(UUID player) {
		return Bukkit.getPlayer(player).getLevel();
	}

	@Override
	public void deposit(UUID player, double amount) {
		Bukkit.getPlayer(player).setLevel(Bukkit.getPlayer(player).getLevel() + (int)amount);
	}

	@Override
	public void withdraw(UUID player, double amount) {
		Bukkit.getPlayer(player).setLevel(Bukkit.getPlayer(player).getLevel() - (int)amount);
	}

}
