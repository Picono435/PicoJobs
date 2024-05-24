package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemImplementation extends EconomyImplementation {

    protected RequiredField<String, ItemType> requiredField;

    public ItemImplementation() {
        this.requiredField = new RequiredField<>("items", new RequiredFieldType<String, ItemType>(String.class, ItemType.class) {
            @Override
            public ItemType toValue(@Nonnull String primitive) {
                return ItemTypes.registry().value(ResourceKey.resolve(primitive));
            }

            @Nonnull
            @Override
            public String toPrimitive(ItemType value) {
                return ItemTypes.registry().valueKey(value).asString();
            }

            @Nonnull
            @Override
            public List<ItemType> getSuggestions() {
                return ItemTypes.registry().stream().collect(Collectors.toList());
            }
        }, true);
    }

    @Override
    public String getName() {
        return "ITEM";
    }

    @Override
    public double getBalance(UUID player) {
        return 0D;
    }

    @Override
    public void deposit(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<ItemType> items = this.requiredField.getValueList(jp.getJob());
        for(ItemType item : items) {
            Sponge.server().player(player).get().inventory().offer(ItemStack.of(item, (int) Math.round(amount)));
        }
    }

    @Override
    public void withdraw(UUID player, double amount) {}

    @Override
    public RequiredField<String, ItemType> getRequiredField() {
        return requiredField;
    }
}
