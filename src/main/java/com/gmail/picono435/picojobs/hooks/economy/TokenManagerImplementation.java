package com.gmail.picono435.picojobs.hooks.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.api.EconomyImplementation;

import me.realized.tokenmanager.api.TokenManager;

public class TokenManagerImplementation extends EconomyImplementation {
	
	public TokenManagerImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("TokenManager");
	}
	
	@Override
	public String getName() {
		return "TOKEN_MANAGER";
	}

	@Override
	public double getBalance(Player player) {
		TokenManager api = (TokenManager) this.requiredPlugin;
		if(api.getTokens(player).isPresent()) {
			return api.getTokens(player).getAsLong();
		}
		return 0;
	}

	@Override
	public void deposit(Player player, double amount) {
		TokenManager api = (TokenManager) this.requiredPlugin;
		api.addTokens(player, (long)amount);
	}

	@Override
	public void withdraw(Player player, double amount) {
		TokenManager api = (TokenManager) this.requiredPlugin;
		api.removeTokens(player, (long)amount);
	}

}
