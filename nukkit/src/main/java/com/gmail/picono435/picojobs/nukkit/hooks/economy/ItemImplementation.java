package com.gmail.picono435.picojobs.nukkit.hooks.economy;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.RuntimeItems;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemImplementation extends EconomyImplementation {

	protected RequiredField<String, Item> requiredField;

	public ItemImplementation() {
		this.requiredField = new RequiredField<>("items", new RequiredFieldType<String, Item>(String.class, Item.class) {
			@Override
			public Item toValue(@Nonnull String primitive) {
				return Item.fromString(primitive);
			}

			@Nonnull
			@Override
			public String toPrimitive(Item value) {
				return value.getName();
			}

			@Nonnull
			@Override
			public List<Item> getSuggestions() {
				return Arrays.stream(ItemID.class.getFields()).map(field -> "minecraft:" + field.getName().toLowerCase(Locale.ROOT)).map(RuntimeItems::getLegacyIdFromLegacyString).map(Item::get).collect(Collectors.toList());
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
		List<Item> items = this.requiredField.getValueList(jp.getJob());
		for(Item item : items) {
			item.setCount((int) Math.round(amount));
			PicoJobsNukkit.getInstance().getServer().getPlayer(player).get().getInventory().addItem(item);
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

	@Override
	public RequiredField<String, Item> getRequiredField() {
		return requiredField;
	}

}
