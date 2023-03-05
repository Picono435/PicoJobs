package com.gmail.picono435.picojobs.hooks;

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.economy.VaultImplementation;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

public class VaultHook {
	
	private static Economy econ;
	
	private static boolean isEnabled = false;
	private static boolean hasEconomy = true;
	
	public static boolean setupVault() {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		isEnabled = true;
		if(!setupEconomy()) {
			new BukkitRunnable() {
				public void run() {
					if(!setupVault()) {
						PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "A economy plugin from VAULT was not found. The VAULT economy type will not work.");
					}
				}
			} .runTask(PicoJobsPlugin.getInstance());
			hasEconomy = false;
		} else {
			PicoJobsAPI.registerEconomy(new VaultImplementation());
			hasEconomy = true;
		}

		return hasEconomy;
	}
	
	private static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public static Economy getEconomy() {
		return econ;
	}
	
	public static boolean hasEconomyPlugin() {
		return hasEconomy;
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
