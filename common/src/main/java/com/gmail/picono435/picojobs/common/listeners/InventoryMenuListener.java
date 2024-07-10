package com.gmail.picono435.picojobs.common.listeners;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;

public class InventoryMenuListener {

	/**
	 * Simulates a click in a inventory
	 *
	 * @param sender the clicker
	 * @param inventoryAdapter the inventory
	 * @param item the item that got clicked
	 * @return true if it is meant to cancel the event
	 */
	public static boolean onBasicClick(Sender sender, InventoryAdapter inventoryAdapter, Object item) {
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(inventoryAdapter.getTitle().equals(FileManager.getGuiNode().node("gui-settings", "choose-job", "title").getString())) {
			ItemAdapter itemAdapter = inventoryAdapter.toItemAdapter(item);
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(itemAdapter.getName());
			if(job == null) return true;
			if(job.requirePermission() && !sender.hasPermission("picojobs.job." + job.getID())) {
				sender.sendMessage(LanguageManager.getMessage("no-permission", sender.getUUID()));
				return true;
			}
			jp.setJob(job);
			sender.sendMessage(LanguageManager.getMessage("choosed-job", sender.getUUID()));
			sender.closeInventory();
			return true;
		}
		
		/*
		 * Need Work & Has Work Menu Clicking Event
		 */
		if(inventoryAdapter.getTitle().equals(FileManager.getGuiNode().node("gui-settings", "need-work", "title").getString()) ||
				inventoryAdapter.getTitle().equals(FileManager.getGuiNode().node("gui-settings", "has-work", "title").getString())) {
			ItemAdapter itemAdapter = inventoryAdapter.toItemAdapter(item);
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(sender.getUUID());
			ClickAction action = WorkMenu.actions.get(itemAdapter.getName());
			if(action == null) return true;
			if(action == ClickAction.SALARY) {
				PicoJobsCommon.getMainInstance().getJobsCommand().getWithdrawCommand().onCommand(null, null, sender);
				sender.closeInventory();
				return true;
			}
			if(action == ClickAction.ACCEPTWORK) {
				sender.sendMessage(LanguageManager.getMessage("accepted-work", sender.getUUID()));
				jp.setWorking(true);
				sender.closeInventory();
				return true;
			}
			if(action == ClickAction.LEAVEJOB) {
				PicoJobsCommon.getMainInstance().getJobsCommand().getLeaveJobCommand().onCommand(null, null, sender);
				sender.closeInventory();
				return true;
			}
			return true;
		}

		return false;
	}
}
