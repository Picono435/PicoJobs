package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.bukkit.hooks.PlayerPointsHook;

import java.util.UUID;

public class PointsImplementation extends EconomyImplementation {
	
	public PointsImplementation() {
		this.requiredPlugin = "PlayerPoints";
	}
	
	@Override
	public String getName() {
		return "POINTS";
	}

	@Override
	public double getBalance(UUID player) {
		return PlayerPointsHook.getPlayerPointsAPI().look(player);
	}

	@Override
	public void deposit(UUID player, double amount) {
		PlayerPointsHook.getPlayerPointsAPI().give(player, (int)amount);
	}

	@Override
	public void withdraw(UUID player, double amount) {
		PlayerPointsHook.getPlayerPointsAPI().take(player, (int)amount);
	}

}
