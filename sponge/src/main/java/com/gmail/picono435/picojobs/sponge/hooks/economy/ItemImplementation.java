package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ItemImplementation extends EconomyImplementation {

    protected RequiredField<String> requiredField;

    public ItemImplementation() {
        this.requiredField = new RequiredField<>("items");
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
        List<String> items = this.requiredField.getValueList(jp, String.class);
        for(String item : items) {
            ItemType itemType = ItemTypes.registry().findValue(ResourceKey.resolve(item)).get();
            Sponge.server().player(player).get().inventory().offer(ItemStack.of(itemType, (int) Math.round(amount)));
        }
    }

    @Override
    public void withdraw(UUID player, double amount) {}

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
