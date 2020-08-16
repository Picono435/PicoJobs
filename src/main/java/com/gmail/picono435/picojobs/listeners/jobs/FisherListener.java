package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class FisherListener implements Listener {
	
	@EventHandler()
	public void onPlayerDeath(PlayerFishEvent e) {
		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(e.getState() != State.CAUGHT_FISH) return;
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.FISHING) return;
		double level = jp.getMethodLevel();
		double method = jp.getMethod();
		jp.setMethod(method + 1);
				
		int reqmethod = (int) (job.getMethod() * level * PicoJobsAPI.getSettingsManager().getKillsFrequency());
		
		if(jp.getMethod() >= reqmethod) {
			double salary = job.getSalary() * level * PicoJobsAPI.getSettingsManager().getSalaryFrequency();
			jp.setMethodLevel(level + 1);
			jp.setMethod(0);
			jp.setWorking(false);
			jp.setSalary(jp.getSalary() + salary);
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
}
