package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.sponge.hooks.SpongeEconomyHook;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class SpongeImplementation extends EconomyImplementation {

    protected RequiredField<String> requiredField;

    public SpongeImplementation() {
        this.requiredPlugin = "spongeapi";
        this.requiredField = new RequiredField<>("currency", "DEFAULT");
    }

    @Override
    public String getName() {
        return "SPONGE";
    }

    @Override
    public double getBalance(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        Optional<UniqueAccount> account = SpongeEconomyHook.getEconomyService().findOrCreateAccount(player);
        if(account.isPresent()) {
            Currency currency = getCurrency(jp);
            if(currency == null) return 0;
            return account.get().balance(currency).doubleValue();
        }
        return 0;
    }

    @Override
    public void deposit(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        UniqueAccount account = SpongeEconomyHook.getEconomyService().findOrCreateAccount(player).get();
        Currency currency = getCurrency(jp);
        account.deposit(currency, new BigDecimal(amount));
    }

    @Override
    public void withdraw(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        UniqueAccount account = SpongeEconomyHook.getEconomyService().findOrCreateAccount(player).get();
        Currency currency = getCurrency(jp);
        account.withdraw(currency, new BigDecimal(amount));
    }

    private Currency getCurrency(JobPlayer jp) {
        String currencyString = this.requiredField.getValue(jp, String.class);
        Currency currency;
        if(currencyString.equals("DEFAULT")) {
            currency = SpongeEconomyHook.getEconomyService().defaultCurrency();
        } else {
            Optional<Registry<Currency>> optionalRegistry = Sponge.game().findRegistry(RegistryTypes.CURRENCY);
            if(optionalRegistry.isPresent()) {
                currency = optionalRegistry.get().findValue(ResourceKey.resolve(currencyString)).orElse(null);
            } else {
                currency = null;
            }
        }
        return currency;
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
