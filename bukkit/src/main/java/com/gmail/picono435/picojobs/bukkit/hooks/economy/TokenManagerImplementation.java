package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import org.bukkit.Bukkit;

import com.gmail.picono435.picojobs.api.EconomyImplementation;

import me.realized.tokenmanager.api.TokenManager;

import java.util.UUID;

public class TokenManagerImplementation extends EconomyImplementation {

	private TokenManager api = (TokenManager) Bukkit.getPluginManager().getPlugin("TokenManager");

	public TokenManagerImplementation() {
		this.requiredPlugin = "TokenManager";
	}
	
	@Override
	public String getName() {
		return "TOKEN_MANAGER";
	}

	@Override
	public double getBalance(UUID player) {
		if(api.getTokens(Bukkit.getPlayer(player)).isPresent()) {
			return api.getTokens(Bukkit.getPlayer(player)).getAsLong();
		}
		return 0;
	}

	@Override
	public void deposit(UUID player, double amount) {
		api.addTokens(Bukkit.getPlayer(player), (long)amount);
	}

	@Override
	public void withdraw(UUID player, double amount) {
		api.removeTokens(Bukkit.getPlayer(player), (long)amount);
	}

}
