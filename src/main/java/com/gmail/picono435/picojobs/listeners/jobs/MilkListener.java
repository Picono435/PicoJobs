package com.gmail.picono435.picojobs.listeners.jobs;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

public class MilkListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTakeMilk(PlayerBucketFillEvent  e) {
		if(e.getPlayer() == null) return;
		try {
			Block b = (Block)e.getClass().getMethod("getBlockClicked").invoke(this);
			if(b.isLiquid()) return;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e1) {
			Block b;
			try {
				b = (Block)e.getClass().getMethod("getBlock").invoke(this);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e2) {
				return;
			}
			if(b.isLiquid()) return;
		};
		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.MILK) return;
		if(jp.simulateEvent(job.getType())) {
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
}
