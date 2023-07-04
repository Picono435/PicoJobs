package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.plugin.PluginManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import com.gmail.picono435.picojobs.nukkit.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.nukkit.listeners.NukkitAliasesListener;
import com.gmail.picono435.picojobs.nukkit.listeners.NukkitExecuteCommandListener;
import com.gmail.picono435.picojobs.nukkit.listeners.NukkitJoinCacheListener;
import com.gmail.picono435.picojobs.nukkit.listeners.jobs.*;

public class NukkitSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch(phase) {
            case ONE: {
                /*PicoJobsAPI.registerEconomy(new ExpImplementation());
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
                PicoJobsAPI.registerWorkZone(new TownyImplementation());*/

                PlaceholderAPIHook.setupPlaceholderAPI();
                break;
            }
            case TWO: {
                PluginManager pluginManager = PicoJobsNukkit.getInstance().getServer().getPluginManager();
                pluginManager.registerEvents(new NukkitAliasesListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new NukkitExecuteCommandListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new NukkitJoinCacheListener(), PicoJobsNukkit.getInstance());

                pluginManager.registerEvents(new BreakListener(), PicoJobsNukkit.getInstance());
                //TODO: TAME JOB TYPE
                //TODO: SHEAR JOB TYPE
                pluginManager.registerEvents(new FillListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new KillListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new FishingListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new PlaceListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new CraftListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new EatListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new EnchantListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new FillEntityListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new MoveListener(), PicoJobsNukkit.getInstance());
                //TODO: TRADE JOB TYPE
                pluginManager.registerEvents(new RepairListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new SmeltListener(), PicoJobsNukkit.getInstance());
                pluginManager.registerEvents(new KillEntityListener(), PicoJobsNukkit.getInstance());
                if(PicoJobsCommon.isMoreThan("1.14")) {
                    //TODO: CHECK THIS
                    pluginManager.registerEvents(new StripLogsListener(), PicoJobsNukkit.getInstance());
                }
                break;
            }
            case THREE: {
                break;
            }
        }
    }
}
