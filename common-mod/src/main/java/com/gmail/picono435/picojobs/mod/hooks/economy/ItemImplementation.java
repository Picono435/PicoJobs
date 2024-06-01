package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemImplementation extends EconomyImplementation {

	protected RequiredField<String, Item> requiredField;
	
	@Override
	public String getName() {
		return "ITEM";
	}

	@Override
	public void onRegister() {
		this.requiredField = new RequiredField<>("items", new RequiredFieldType<>(String.class, Item.class) {
			@Override
			public Item toValue(@Nonnull String primitive) {
				return BuiltInRegistries.ITEM.get(new ResourceLocation(primitive));
			}

			@Nonnull
			@Override
			public String toPrimitive(Item value) {
				return BuiltInRegistries.ITEM.getKey(value).toString();
			}

			@Nonnull
			@Override
			public List<Item> getSuggestions() {
				return List.of();
			}
		}, true);
	}

	@Override
	public double getBalance(UUID player) {
		return 0D;
	}

	@Override
	public void deposit(UUID player, double amount) {
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
		List<Item> items = this.requiredField.getValueList(jp.getJob());
		for(Item item : items) {
			PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).getInventory().add(new ItemStack(item, (int) Math.round(amount)));
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

	@Override
	public RequiredField<String, Item> getRequiredField() {
		return requiredField;
	}
}
