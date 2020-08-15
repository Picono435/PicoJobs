package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class KillerListener implements Listener {
	
	@EventHandler()
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() == null) return;
		Player p = e.getEntity().getKiller();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.KILL && job.getType() != Type.KILL_JOB) return;
		
		if(job.getType() == Type.KILL_JOB) {
			JobPlayer jdead = PicoJobsAPI.getPlayersManager().getJobPlayer(e.getEntity());
			if(jdead.getJob() == null) return;
			if(!jdead.getJob().getName().equals(job.getName())) return;
		}
		double level = jp.getMethodLevel();
		double method = jp.getMethod();
		jp.setMethod(method + 1);
				
		double reqmethod = job.getMethod() * level * PicoJobsAPI.getSettingsManager().getMethodFrequency();
		
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
