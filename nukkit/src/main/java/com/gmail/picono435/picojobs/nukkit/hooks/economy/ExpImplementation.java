package com.gmail.picono435.picojobs.nukkit.hooks.economy;

import cn.nukkit.Player;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import java.util.UUID;

public class ExpImplementation extends EconomyImplementation {

	@Override
	public String getName() {
		return "EXP";
	}

	@Override
	public double getBalance(UUID player) {
		return PicoJobsNukkit.getInstance().getServer().getPlayer(player).get().getExperienceLevel();
	}

	@Override
	public void deposit(UUID player, double amount) {
		Player onlinePlayer = PicoJobsNukkit.getInstance().getServer().getPlayer(player).get();
		onlinePlayer.setExperience(onlinePlayer.getExperience(), (int) (onlinePlayer.getExperienceLevel() + amount));
	}

	@Override
	public void withdraw(UUID player, double amount) {
		Player onlinePlayer = PicoJobsNukkit.getInstance().getServer().getPlayer(player).get();
		onlinePlayer.setExperience(onlinePlayer.getExperience(), (int) (onlinePlayer.getExperienceLevel() - amount));
	}

}
