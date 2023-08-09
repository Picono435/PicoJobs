package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import com.gmail.picono435.picojobs.bukkit.hooks.VaultHook;
import org.bukkit.Bukkit;

import com.gmail.picono435.picojobs.api.EconomyImplementation;

import java.util.UUID;

public class VaultImplementation extends EconomyImplementation {
	
	public VaultImplementation() {
		this.requiredPlugin = "Vault";
	}
	
	@Override
	public String getName() {
		return "VAULT";
	}

	@Override
	public double getBalance(UUID player) {
		return VaultHook.getEconomy().getBalance(Bukkit.getOfflinePlayer(player));
	}

	@Override
	public void deposit(UUID player, double amount) {
		VaultHook.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(player), amount);
	}

	@Override
	public void withdraw(UUID player, double amount) {
		VaultHook.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(player), amount);
	}

}
