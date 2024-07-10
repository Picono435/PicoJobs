package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import dev.the_fireplace.annotateddi.api.Injectors;
import dev.the_fireplace.grandeconomy.api.injectables.Economy;

import java.util.UUID;

public class GrandEconomyImplementation extends EconomyImplementation {

    private Economy economy;

    public GrandEconomyImplementation() {
        this.requiredPlugin = "grandeconomy";
    }

    @Override
    public String getName() {
        return "GRAND_ECONOMY";
    }

    @Override
    public double getBalance(UUID player) {
        if(this.economy == null) {
            this.economy = Injectors.INSTANCE.getAutoInjector("grandeconomy").getInstance(Economy.class);
        }
        return this.economy.getBalance(player, true);
    }

    @Override
    public void deposit(UUID player, double amount) {
        if(this.economy == null) {
            this.economy = Injectors.INSTANCE.getAutoInjector("grandeconomy").getInstance(Economy.class);
        }
        this.economy.addToBalance(player, amount, true);
    }

    @Override
    public void withdraw(UUID player, double amount) {
        if(this.economy == null) {
            this.economy = Injectors.INSTANCE.getAutoInjector("grandeconomy").getInstance(Economy.class);
        }
        this.economy.takeFromBalance(player, amount, true);
    }
}
