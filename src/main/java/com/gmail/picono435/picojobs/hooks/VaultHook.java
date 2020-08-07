package com.gmail.picono435.picojobs.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHook {
	
	private static Permission perm;
	private static Economy econ;
	
	private static boolean isEnabled = false;
	private static boolean hasPermission = true;
	private static boolean hasEconomy = true;
	
	public static void setupVault() {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] The optional dependency Vault was not found. Some features may not work well!");
			return;
		}
		isEnabled = true;
		PicoJobsPlugin.sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] Vault was found! We are configuring the connection between us and Vault.");
		if(!setupPermission()) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] We did not find any permissions plugin that works with Vault on the server. Some features may not work well.");
			hasPermission = false;
		}
		if(!setupEconomy()) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] We did not find any economy plugin that works with Vault on the server. Some features may not work well.");
			hasEconomy = false;
		}
	}
	
	private static boolean setupPermission() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        perm = rsp.getProvider();
        return perm != null;
    }
	
	public static Permission getPermission() {
		return perm;
	}
	
	public static boolean hasPermissionPlugin() {
		return hasPermission;
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
	
	public static Permission getEconomy() {
		return perm;
	}
	
	public static boolean hasEconomyPlugin() {
		return hasEconomy;
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
}
