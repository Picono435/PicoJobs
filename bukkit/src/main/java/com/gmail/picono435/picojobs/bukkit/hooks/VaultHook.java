package com.gmail.picono435.picojobs.bukkit.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.bukkit.hooks.economy.VaultImplementation;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
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
                        PicoJobsCommon.getLogger().warning("A economy plugin from VAULT was not found. The VAULT economy type will not work.");
                    }
                }
            } .runTask(PicoJobsBukkit.getInstance());
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
