package com.gmail.picono435.picojobs.mod.platform.forge;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.mod.forge.hooks.SpongeEconomyHook;

public class ModSoftwareHookerImpl {
    public static void hookInPhaseSpecific(SoftwareHooker.Phase phase) {
        switch (phase) {
            case ONE -> {
                if(PicoJobsCommon.getPlatformAdapter().isPluginEnabled("spongeapi")) {
                    SpongeEconomyHook.setupSpongeEconomy();
                }
            }
            case TWO -> {
            }
            case THREE -> {
            }
        }
    }
}
