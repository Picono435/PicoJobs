package com.gmail.picono435.picojobs.bukkit.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class CommandImplementation extends EconomyImplementation {

	public CommandImplementation() {
		this.requiredPlugin = "PicoJobs";
		this.requiredField = new RequiredField("commands", RequiredField.RequiredFieldType.STRING_LIST);
	}
	
	@Override
	public String getName() {
		return "COMMAND";
	}

	@Override
	public double getBalance(UUID player) {
		return 0D;
	}

	@Override
	public void deposit(UUID player, double amount) {
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
		List<String> commands;
		try {
			commands = FileManager.getJobsNode().node("jobs", jp.getJob().getID(), "commands").getList(String.class);
		} catch (SerializationException event) {
			throw new RuntimeException(event);
		}
		for(String command : commands) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command
					.replace("%amount%", String.valueOf(amount))
					.replace("%player%", Bukkit.getPlayer(player).getName())
			);
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

}
