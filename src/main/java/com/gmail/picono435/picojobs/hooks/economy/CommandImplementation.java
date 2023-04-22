package com.gmail.picono435.picojobs.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandImplementation extends EconomyImplementation {

	public CommandImplementation() {
		this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PicoJobs");
		this.requiredField = new RequiredField("commands", RequiredField.RequiredFieldType.STRING_LIST);
	}
	
	@Override
	public String getName() {
		return "COMMAND";
	}

	@Override
	public double getBalance(Player player) {
		return 0D;
	}

	@Override
	public void deposit(Player player, double amount) {
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
		List<String> commands = FileCreator.getJobsConfig().getStringList("jobs." + jp.getJob().getID() + "." + "commands");
		for(String command : commands) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command
					.replace("%amount%", amount + "")
					.replace("%player%", player.getName())
			);
		}
	}

	@Override
	public void withdraw(Player player, double amount) {}

}
