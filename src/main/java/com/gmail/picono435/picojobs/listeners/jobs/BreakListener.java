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
		if(PicoJobsAPI.getSettingsManager().isAllowPlaced() && e.getBlock().getMetadata("PLACED").size() > 0) return;

		Player p = e.getPlayer();
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(!jp.hasJob()) return;
		if(!jp.isWorking()) return;
		Job job = jp.getJob();
		if(!job.getTypes().contains(Type.BREAK)) return;
		if(PicoJobsPlugin.getInstance().isLessThan("1.12.2")) {
			if(e.getBlock().getState().getData() instanceof Crops) {
				if(((Crops)e.getBlock().getState().getData()).getState() != CropState.RIPE) return;
			}
		} else {
			if(e.getBlock().getBlockData() instanceof Ageable) {
				Ageable ageable = (Ageable) e.getBlock().getBlockData();
				if(ageable.getMaximumAge() != ageable.getAge()) return;
			}
		}

		if(!job.inWhitelist(Type.BREAK, e.getBlock().getType())) return;

		if(jp.simulateEvent()) {
			p.sendMessage(LanguageManager.getMessage("finished-work", p));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlaceBlock(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BEETROOT_SEEDS ||
				e.getBlock().getType() == Material.MELON_SEEDS ||
				e.getBlock().getType() == Material.PUMPKIN_SEEDS ||
				e.getBlock().getType() == Material.WHEAT_SEEDS ||
				e.getBlock().getType() == Material.CARROT ||
				e.getBlock().getType() == Material.POTATO ||
				e.getBlock().getType() == Material.WHEAT
		) return;
		e.getBlock().setMetadata("PLACED", new FixedMetadataValue(PicoJobsPlugin.getInstance(), "YES"));
	}
}
