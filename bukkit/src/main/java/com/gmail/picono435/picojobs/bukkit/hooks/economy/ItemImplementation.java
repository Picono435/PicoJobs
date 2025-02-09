package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemImplementation extends EconomyImplementation {

	protected RequiredField<String, Material> requiredField;
	
	@Override
	public String getName() {
		return "ITEM";
	}

	@Override
	public void onRegister() {
		this.requiredField = new RequiredField<>("items", new RequiredFieldType<String, Material>(String.class, Material.class) {
			@Override
			public Material toValue(@Nonnull String primitive) {
				return PicoJobsBukkit.getNamespacedUtils().matchMaterial(primitive);
			}

			@Nonnull
			@Override
			public String toPrimitive(Material value) {
				return PicoJobsBukkit.getNamespacedUtils().getKeyFromObject(value);
			}

			@Nonnull
			@Override
			public List<Material> getSuggestions() {
				return PicoJobsBukkit.getNamespacedUtils().getMaterials();
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
		List<Material> items = this.requiredField.getValueList(jp.getJob());
		for(Material item : items) {
			Bukkit.getPlayer(player).getInventory().addItem(new ItemStack(item, (int) Math.round(amount)));
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

	@Override
	public RequiredField<String, Material> getRequiredField() {
		return requiredField;
	}

}
