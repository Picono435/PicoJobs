package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.mod.hooks.economy.CommandImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.ExpImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.GrandEconomyImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.ItemImplementation;
import com.gmail.picono435.picojobs.mod.hooks.workzones.BiomeImplementation;
import com.gmail.picono435.picojobs.mod.hooks.workzones.FlanImplementation;
import com.gmail.picono435.picojobs.mod.hooks.workzones.WorldImplementation;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class ModSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch(phase) {
            case ONE -> {
                PicoJobsAPI.registerEconomy(new ExpImplementation());
                PicoJobsAPI.registerEconomy(new CommandImplementation());
                PicoJobsAPI.registerEconomy(new ItemImplementation());
                PicoJobsAPI.registerEconomy(new GrandEconomyImplementation());

                PicoJobsAPI.registerWorkZone(new BiomeImplementation());
                PicoJobsAPI.registerWorkZone(new WorldImplementation());
                PicoJobsAPI.registerWorkZone(new FlanImplementation());
            }
            case TWO -> {
            }
            case THREE -> {
            }
        }
        hookInPhaseSpecific(phase);
    }

    @ExpectPlatform
    public static void hookInPhaseSpecific(Phase phase) {
        throw new AssertionError();
    }
}
