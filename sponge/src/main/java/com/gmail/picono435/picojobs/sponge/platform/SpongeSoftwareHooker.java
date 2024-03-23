package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import com.gmail.picono435.picojobs.sponge.hooks.SpongeEconomyHook;
import com.gmail.picono435.picojobs.sponge.hooks.economy.CommandImplementation;
import com.gmail.picono435.picojobs.sponge.hooks.economy.ExpImplementation;
import com.gmail.picono435.picojobs.sponge.hooks.economy.ItemImplementation;
import com.gmail.picono435.picojobs.sponge.hooks.workzones.BiomeImplementation;
import com.gmail.picono435.picojobs.sponge.hooks.workzones.GriefDefenderImplementation;
import com.gmail.picono435.picojobs.sponge.hooks.workzones.WorldImplementation;
import com.gmail.picono435.picojobs.sponge.listeners.SpongeExecuteCommandListener;
import com.gmail.picono435.picojobs.sponge.listeners.SpongeJoinCacheListener;
import com.gmail.picono435.picojobs.sponge.listeners.jobs.*;
import org.spongepowered.api.Sponge;

public class SpongeSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch (phase) {
            case ONE:
                PicoJobsAPI.registerEconomy(new ExpImplementation());
                PicoJobsAPI.registerEconomy(new CommandImplementation());
                PicoJobsAPI.registerEconomy(new ItemImplementation());
                SpongeEconomyHook.setupSpongeEconomy();

                PicoJobsAPI.registerWorkZone(new BiomeImplementation());
                PicoJobsAPI.registerWorkZone(new WorldImplementation());
                PicoJobsAPI.registerWorkZone(new GriefDefenderImplementation());
                break;
            case TWO:
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new SpongeExecuteCommandListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new SpongeJoinCacheListener());

                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new BreakListener());
                // TODO: EAT LISTENER
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new EnchantListener());
                // TODO: FILL ENTITY LISTENER
                // TODO: FILL LISTENER
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new FishingListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new KillEntityListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new KillListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new MoveListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new PlaceListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new CraftListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new RepairListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new ShearListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new SmeltListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new StripLogsListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new TameListener());
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new TradeListener());
                break;
            case THREE:
                break;
        }
    }
}
