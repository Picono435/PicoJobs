package com.gmail.picono435.picojobs.mod.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;

import java.util.UUID;

public class ExpImplementation extends EconomyImplementation {
	
	public ExpImplementation() {
		this.requiredPlugin = "PicoJobs";
	}
	
	@Override
	public String getName() {
		return "EXP";
	}

	@Override
	public double getBalance(UUID player) {
		return PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).experienceLevel;
	}

	@Override
	public void deposit(UUID player, double amount) {
		PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).giveExperienceLevels((int)amount);
	}

	@Override
	public void withdraw(UUID player, double amount) {
		PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).giveExperienceLevels(-(int)amount);
	}

}
