package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class BreakListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreakBlock(BlockBreakEvent e) {
		if(e.getPlayer() == null) return;
		
		if(e.getBlock().getMetadata("PLACED").size() > 0) return;
		
		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(job.getType() != Type.BREAK) return;
		
		if(!job.inWhitelist(e.getBlock().getType())) return;
		
		if(jp.simulateEvent()) {
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent e) {
		if(e.getPlayer() == null) return;
		e.getBlock().setMetadata("PLACED", new FixedMetadataValue(PicoJobsPlugin.getInstance(), "YES"));
	}
}
