package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryMenuListener implements Listener {
	
	public static Map<ItemStack, ClickAction> actionItems = new HashMap<>();
	
	@EventHandler()
	public void onBasicClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(event.getView().getTitle().equals(FileManager.getGuiNode().node("gui-settings", "choose-job", "title").getString())) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(event.getCurrentItem().getItemMeta().getDisplayName());
			if(job == null) return;
			if(job.requirePermission() && !player.hasPermission("picojobs.job." + job.getID())) {
				player.sendMessage(LanguageManager.getMessage("no-permission", player.getUniqueId()));
				return;
			}
			jp.setJob(job);
			player.sendMessage(LanguageManager.getMessage("choosed-job", player.getUniqueId()));
			player.closeInventory();
			return;
		}
		
		/*
		 * Need Work & Has Work Menu Clicking Event
		 */
		if(event.getView().getTitle().equals(FileManager.getGuiNode().node("gui-settings", "need-work", "title").getString()) ||
				event.getView().getTitle().equals(FileManager.getGuiNode().node("gui-settings", "has-work", "title").getString())) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId());
			ClickAction action = actionItems.get(event.getCurrentItem());
			if(action == null) return;
			if(action == ClickAction.SALARY) {
				PicoJobsCommon.getMainInstance().getJobsCommand().getWithdrawCommand().onCommand(null, null, new BukkitSender(player));
				actionItems.remove(event.getCurrentItem());
				player.closeInventory();
				return;
			}
			if(action == ClickAction.ACCEPTWORK) {
				player.sendMessage(LanguageManager.getMessage("accepted-work", player.getUniqueId()));
				jp.setWorking(true);
				actionItems.remove(event.getCurrentItem());
				player.closeInventory();
				return;
			}
			if(action == ClickAction.LEAVEJOB) {
				PicoJobsCommon.getMainInstance().getJobsCommand().getLeaveJobCommand().onCommand(null, null, new BukkitSender(player));
				actionItems.remove(event.getCurrentItem());
				player.closeInventory();
				return;
			}
		}
	}
}
