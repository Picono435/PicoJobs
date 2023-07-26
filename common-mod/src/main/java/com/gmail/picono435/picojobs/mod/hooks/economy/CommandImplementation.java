package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
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
			PicoJobsMod.getServer().get().getCommands().performCommand(PicoJobsMod.getServer().get().getCommands().getDispatcher().parse(command, PicoJobsMod.getServer().get().createCommandSourceStack()), command
					.replace("%amount%", String.valueOf(amount))
					.replace("%player%", PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).getName().getString())
			);
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

}
