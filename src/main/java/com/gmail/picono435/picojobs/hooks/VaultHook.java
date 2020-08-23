package com.gmail.picono435.picojobs.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.economy.VaultImplementation;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class VaultHook {
	
	private static Economy econ;
	
	private static boolean isEnabled = false;
	private static boolean hasEconomy = true;
	
	public static void setupVault() {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] The economy plugin Vault was not found. The VAULT economy will not be enabled.");
			return;
		}
		isEnabled = true;
		//PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] Vault was found! We are configuring the VAULT economy implementation.");
		if(!setupEconomy()) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] The economy plugin Vault was not found. The VAULT economy will not be enabled.");
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
