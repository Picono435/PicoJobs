package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.bukkit.command.BukkitJobsAdminCommand;
import com.gmail.picono435.picojobs.bukkit.command.BukkitJobsCommand;
import com.gmail.picono435.picojobs.bukkit.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.bukkit.hooks.PlayerPointsHook;
import com.gmail.picono435.picojobs.bukkit.hooks.VaultHook;
import com.gmail.picono435.picojobs.bukkit.hooks.economy.*;
import com.gmail.picono435.picojobs.bukkit.hooks.workzones.*;
import com.gmail.picono435.picojobs.bukkit.listeners.*;
import com.gmail.picono435.picojobs.bukkit.listeners.jobs.*;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import org.bukkit.Bukkit;

public class BukkitSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch(phase) {
            case ONE -> {
                PicoJobsAPI.registerEconomy(new ExpImplementation());
                PicoJobsAPI.registerEconomy(new CommandImplementation());
                PicoJobsAPI.registerEconomy(new ItemImplementation());
                VaultHook.setupVault();
                PlayerPointsHook.setupPlayerPoints();
                PicoJobsAPI.registerEconomy(new TokenManagerImplementation());

                PicoJobsAPI.registerWorkZone(new BiomeImplementation());
                PicoJobsAPI.registerWorkZone(new WorldImplementation());
                PicoJobsAPI.registerWorkZone(new WorldGuardImplementation());
                PicoJobsAPI.registerWorkZone(new GriefPreventionImplementation());
                PicoJobsAPI.registerWorkZone(new GriefDefenderImplementation());
                PicoJobsAPI.registerWorkZone(new TownyImplementation());

                PlaceholderAPIHook.setupPlaceholderAPI();
            }
            case TWO -> {
                PicoJobsBukkit.getInstance().getCommand("jobs").setExecutor(new BukkitJobsCommand());
                PicoJobsBukkit.getInstance().getCommand("jobsadmin").setExecutor(new BukkitJobsAdminCommand());

                Bukkit.getPluginManager().registerEvents(new AliasesListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new ExecuteCommandListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new BukkitInventoryMenuListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new BukkitJoinCacheListener(), PicoJobsBukkit.getInstance());

                Bukkit.getPluginManager().registerEvents(new BreakListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new TameListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new ShearListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new FillListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new KillListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new FishingListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new PlaceListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new CraftListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new EatListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new EnchantListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new MilkListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new MoveListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new TradeListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new RepairListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new SmeltListener(), PicoJobsBukkit.getInstance());
                Bukkit.getPluginManager().registerEvents(new KillEntityListener(), PicoJobsBukkit.getInstance());
                if(PicoJobsCommon.isMoreThan("1.14")) {
                    Bukkit.getPluginManager().registerEvents(new StripLogsListener(), PicoJobsBukkit.getInstance());
                }
            }
            case THREE -> {

            }
        }
    }
}
