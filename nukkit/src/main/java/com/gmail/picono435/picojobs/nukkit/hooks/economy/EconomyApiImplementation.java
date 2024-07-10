package com.gmail.picono435.picojobs.nukkit.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import me.onebone.economyapi.EconomyAPI;

import java.util.UUID;

public class EconomyApiImplementation extends EconomyImplementation {

    public EconomyApiImplementation() {
        this.requiredPlugin = "EconomyAPI";
    }

    @Override
    public String getName() {
        return "ECONOMYAPI";
    }

    @Override
    public double getBalance(UUID player) {
        return EconomyAPI.getInstance().myMoney(player);
    }

    @Override
    public void deposit(UUID player, double amount) {
        EconomyAPI.getInstance().addMoney(player, amount);
    }

    @Override
    public void withdraw(UUID player, double amount) {
        EconomyAPI.getInstance().reduceMoney(player, amount);
    }
}
