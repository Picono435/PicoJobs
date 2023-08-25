package com.gmail.picono435.picojobs.sponge.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.sponge.hooks.economy.SpongeImplementation;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.economy.EconomyService;

import java.util.Optional;

public class SpongeEconomyHook {

    private static EconomyService economyService;

    public static void setupSpongeEconomy() {
        Optional<EconomyService> serviceOpt = Sponge.server().serviceProvider().economyService();
        if (!serviceOpt.isPresent()) {
            return;
        }
        economyService = serviceOpt.get();
        PicoJobsAPI.registerEconomy(new SpongeImplementation());
    }

    public static EconomyService getEconomyService() {
        return economyService;
    }
}
