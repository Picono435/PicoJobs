package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
			Item itemObject = BuiltInRegistries.ITEM.get(new ResourceLocation(item));
			PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).getInventory().add(new ItemStack(itemObject, (int) Math.round(amount)));
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

}
