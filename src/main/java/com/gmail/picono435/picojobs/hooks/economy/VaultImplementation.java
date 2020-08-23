package com.gmail.picono435.picojobs.hooks.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.hooks.VaultHook;

public class VaultImplementation extends EconomyImplementation {
	
	public VaultImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("Vault");
	}
	
	@Override
	public String getName() {
		return "VAULT";
	}

	@Override
	public double getBalance(Player player) {
		return VaultHook.getEconomy().getBalance(player);
	}

	@Override
	public void deposit(Player player, double amount) {
		VaultHook.getEconomy().depositPlayer(player, amount);
	}

	@Override
	public void withdraw(Player player, double amount) {
		VaultHook.getEconomy().withdrawPlayer(player, amount);
	}

}
