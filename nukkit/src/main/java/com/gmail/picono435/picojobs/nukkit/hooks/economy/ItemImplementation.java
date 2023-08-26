package com.gmail.picono435.picojobs.nukkit.hooks.economy;

import cn.nukkit.item.Item;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

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
			Item material = Item.fromString(item);
			material.setCount((int) Math.round(amount));
			PicoJobsNukkit.getInstance().getServer().getPlayer(player).get().getInventory().addItem(material);
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

	@Override
	public RequiredField<String> getRequiredField() {
		return requiredField;
	}

}
