package com.gmail.picono435.picojobs.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.utils.FileCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemImplementation extends EconomyImplementation {

	public ItemImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PicoJobs");
		this.requiredField = new RequiredField("items", RequiredFieldType.STRING_LIST);
	}
	
	@Override
	public String getName() {
		return "ITEM";
	}

	@Override
	public double getBalance(Player player) {
		return 0D;
	}

	@Override
	public void deposit(Player player, double amount) {
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
		List<String> items = FileCreator.getJobsConfig().getStringList("jobs." + jp.getJob().getID() + "." + "items");
		for(String item : items) {
			Material material = Material.matchMaterial(item);
			player.getInventory().addItem(new ItemStack(material, (int) Math.round(amount)));
		}
	}

	@Override
	public void withdraw(Player player, double amount) {}

}
