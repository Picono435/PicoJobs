package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import org.spongepowered.api.Sponge;

import java.util.UUID;

public class ExpImplementation extends EconomyImplementation {

    @Override
    public String getName() {
        return "EXP";
    }

    @Override
    public double getBalance(UUID player) {
        return Sponge.server().player(player).get().experienceLevel().get();
    }

    @Override
    public void deposit(UUID player, double amount) {
        Sponge.server().player(player).get().experienceLevel().set((int) (getBalance(player) + amount));
    }

    @Override
    public void withdraw(UUID player, double amount) {
        Sponge.server().player(player).get().experienceLevel().set((int) (getBalance(player) - amount));
    }

}