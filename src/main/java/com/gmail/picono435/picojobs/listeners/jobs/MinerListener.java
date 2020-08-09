package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class MinerListener implements Listener {
	
	@EventHandler()
	public void onBreakBlock(BlockBreakEvent e) {
		if(e.getPlayer() == null) return;
		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.MINER) return;
		double level = jp.getMethodLevel();
		double method = jp.getMethod();
		jp.setMethod(method + 1);
				
		double reqmethod = jp.getMethod() * level;
		
		if(reqmethod >= job.getMethod()) {
			double salary = job.getSalary() * level;
			jp.setMethodLevel(level + 1);
			jp.setMethod(0);
			jp.setWorking(false);
			jp.setSalary(jp.getSalary() + salary);
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
}
