package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;

import java.util.List;
import java.util.UUID;

public class CommandImplementation extends EconomyImplementation {

	protected RequiredField<String> requiredField;

	public CommandImplementation() {
		this.requiredField = new RequiredField<>("commands");
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
		List<String> commands = this.requiredField.getValueList(jp, String.class);
		for(String command : commands) {
			PicoJobsMod.getServer().get().getCommands().performCommand(PicoJobsMod.getServer().get().getCommands().getDispatcher().parse(command, PicoJobsMod.getServer().get().createCommandSourceStack()), command
					.replace("%amount%", String.valueOf(amount))
					.replace("%player%", PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).getName().getString())
			);
		}
	}

	@Override
	public void withdraw(UUID player, double amount) {}

	@Override
	public RequiredField<String> getRequiredField() {
		return requiredField;
	}

}
