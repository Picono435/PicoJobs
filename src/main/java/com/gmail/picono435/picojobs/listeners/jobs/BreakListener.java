package com.gmail.picono435.picojobs.listeners.jobs;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;
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
		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.BREAK)) return;
		if(!PicoJobsAPI.getWorkZone(job.getWorkZone()).isInWorkZone(e.getPlayer())) return;
		boolean isNaturalBlock = e.getBlock().getMetadata("PLACED").size() == 0;
		if(PicoJobsPlugin.getInstance().isLegacyServer()) {
			if(e.getBlock().getState().getData() instanceof Crops) {
				if (((Crops) e.getBlock().getState().getData()).getState() != CropState.RIPE) return;
				isNaturalBlock = true;
			}
		} else {
			if(e.getBlock().getBlockData() instanceof Ageable) {
				Ageable ageable = (Ageable) e.getBlock().getBlockData();
				if(ageable.getMaximumAge() != ageable.getAge()) return;
				isNaturalBlock = true;
			}
		}
		if(!isNaturalBlock) return;

		if(!job.inWhitelist(Type.BREAK, e.getBlock().getType())) return;

		if(jp.simulateEvent()) {
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent e) {
		e.getBlock().setMetadata("PLACED", new FixedMetadataValue(PicoJobsPlugin.getInstance(), "YES"));
	}
}
