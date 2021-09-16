package com.gmail.picono435.picojobs.hooks.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.hooks.PlayerPointsHook;

public class PointsImplementation extends EconomyImplementation {
	
	public PointsImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
	}
	
	@Override
	public String getName() {
		return "POINTS";
	}

	@Override
	public double getBalance(Player player) {
		return PlayerPointsHook.getPlayerPointsAPI().look(player.getUniqueId());
	}

	@Override
	public void deposit(Player player, double amount) {
		PlayerPointsHook.getPlayerPointsAPI().give(player.getUniqueId(), (int)amount);
	}

	@Override
	public void withdraw(Player player, double amount) {
		PlayerPointsHook.getPlayerPointsAPI().take(player.getUniqueId(), (int)amount);
	}

}
