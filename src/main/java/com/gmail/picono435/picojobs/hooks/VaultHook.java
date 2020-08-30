package com.gmail.picono435.picojobs.hooks;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.economy.VaultImplementation;

import net.milkbowl.vault.economy.Economy;

public class VaultHook {
	
	private static Economy econ;
	
	private static boolean isEnabled = false;
	private static boolean hasEconomy = true;
	
	public static void setupVault() {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return;
		}
		isEnabled = true;
		if(!setupEconomy()) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "A economy plugin from VAULT was not found. The VAULT economy type will not work.");
			hasEconomy = false;
		}
		
		if(hasEconomy) {
			PicoJobsAPI.registerEconomy(new VaultImplementation());
		}
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
