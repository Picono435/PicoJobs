package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.mod.hooks.economy.CommandImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.ExpImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.GrandEconomyImplementation;
import com.gmail.picono435.picojobs.mod.hooks.economy.ItemImplementation;

public class ModSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch(phase) {
            case ONE -> {
                PicoJobsAPI.registerEconomy(new ExpImplementation());
                PicoJobsAPI.registerEconomy(new CommandImplementation());
                PicoJobsAPI.registerEconomy(new ItemImplementation());
                PicoJobsAPI.registerEconomy(new GrandEconomyImplementation());
            }
            case TWO -> {
            }
            case THREE -> {
            }
        }
    }
}
