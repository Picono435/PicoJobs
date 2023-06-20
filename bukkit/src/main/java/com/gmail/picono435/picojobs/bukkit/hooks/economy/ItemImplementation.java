package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class ItemImplementation extends EconomyImplementation {

	public ItemImplementation() {
		this.requiredPlugin = "PicoJobs";
		this.requiredField = new RequiredField("items", RequiredField.RequiredFieldType.STRING_LIST);
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
		List<String> items = null;
		try {
			items = FileManager.getJobsNode().node("jobs", jp.getJob().getID(), "items").getList(String.class);
		} catch (SerializationException event) {
			throw new RuntimeException(event);
		}
		for(String item : items) {
			Material material = Material.matchMaterial(item);
			Bukkit.getPlayer(player).getInventory().addItem(new ItemStack(material, (int) Math.round(amount)));
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

}
