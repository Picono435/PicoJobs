package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.sponge.hooks.SpongeEconomyHook;
import net.kyori.adventure.key.Key;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpongeImplementation extends EconomyImplementation {

    protected RequiredField<String, Currency> requiredField;

    public SpongeImplementation() {
        this.requiredPlugin = "spongeapi";
        this.requiredField = new RequiredField<>("currency", new RequiredFieldType<String, Currency>(String.class, Currency.class) {
            @Nullable
            @Override
            public Currency toValue(@Nonnull String primitive) {
                if(primitive.equalsIgnoreCase("DEFAULT")) return SpongeEconomyHook.getEconomyService().defaultCurrency();
                Optional<Registry<Currency>> optionalRegistry = Sponge.game().findRegistry(RegistryTypes.CURRENCY);
                return optionalRegistry.map(currencyRegistry -> currencyRegistry.findValue(ResourceKey.resolve(primitive)).orElse(null)).orElse(null);
            }

            @Nonnull
            @Override
            public String toPrimitive(@Nullable Currency value) {
                Optional<Registry<Currency>> optionalRegistry = Sponge.game().findRegistry(RegistryTypes.CURRENCY);
                if(optionalRegistry.isPresent()) {
                    Optional<ResourceKey> resourceKey = optionalRegistry.get().findValueKey(value);
                    return resourceKey.map(Key::value).orElse("DEFAULT");
                } else {
                    return "DEFAULT";
                }
            }

            @Nonnull
            @Override
            public List<Currency> getSuggestions() {
                Optional<Registry<Currency>> optionalRegistry = Sponge.game().findRegistry(RegistryTypes.CURRENCY);
                return optionalRegistry.map(currencyRegistry -> currencyRegistry.stream().collect(Collectors.toList())).orElseGet(ArrayList::new);
            }
        }, false, "DEFAULT");
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
            Currency currency = this.requiredField.getValue(jp.getJob());
            if(currency == null) return 0;
            return account.get().balance(currency).doubleValue();
        }
        return 0;
    }

    @Override
    public void deposit(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        UniqueAccount account = SpongeEconomyHook.getEconomyService().findOrCreateAccount(player).get();
        Currency currency = this.requiredField.getValue(jp.getJob());
        account.deposit(currency, new BigDecimal(amount));
    }

    @Override
    public void withdraw(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        UniqueAccount account = SpongeEconomyHook.getEconomyService().findOrCreateAccount(player).get();
        Currency currency = this.requiredField.getValue(jp.getJob());
        account.withdraw(currency, new BigDecimal(amount));
    }

    @Override
    public RequiredField<String, Currency> getRequiredField() {
        return requiredField;
    }
}
