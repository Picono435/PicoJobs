package com.gmail.picono435.picojobs.listeners.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.WhitelistConf;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.GUISettingsMenu;
import com.gmail.picono435.picojobs.menu.JobSettingsMenu;
import com.gmail.picono435.picojobs.menu.ActionEnum;
import com.gmail.picono435.picojobs.menu.ActionMenu;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.OtherUtils;

import mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.ChatColor;

public class JobSettingsListener implements Listener {
	
	private static Map<Player, ActionMenu> menuActions  = new HashMap<Player, ActionMenu>();

	@EventHandler()
	public void onSettingsClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if(!p.hasPermission("picojobs.admin")) return;
		
		/*
		 * General Settings Click
		 */
		if(JobSettingsMenu.generalInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			switch(e.getSlot()) {
			case(12): {
				p.closeInventory();
				JobSettingsMenu.openJobsList(p);
				return;
			}
			case(14): {
				p.closeInventory();
				GUISettingsMenu.openGeneral(p);
				return;
			}
			}
			return;
		}
		
		/*
		 * Job List Settings Click
		 */
		if(JobSettingsMenu.jobListInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
			if(job == null) {
				if(e.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GRAY + "Click to go to the next page.")) {
					int page = Integer.parseInt(e.getView().getTitle().split("\\[")[1].replace("]", "")) - 1;
					JobSettingsMenu.openJobsList(p, page + 1);
					return;
				}
				if(e.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GRAY + "Click to go to the previous page.")) {
					int page = Integer.parseInt(e.getView().getTitle().split("\\[")[1].replace("]", "")) - 1;
					if(page == 0) return;
					JobSettingsMenu.openJobsList(p, page - 1);
					return;
				}
				return;
			}
			
			p.closeInventory();
			JobSettingsMenu.openJobSettings(p, job);
			return;
		}
		
		/*
		 * Job Settings Click
		 */
		if(JobSettingsMenu.jobSettingsInventories.containsKey(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = JobSettingsMenu.jobSettingsInventories.get(e.getInventory());
			
			switch(e.getSlot()) {
			case(13): {
				p.closeInventory();
				JobSettingsMenu.openJobEdit(p, job);
				return;
			}
			case(15): {
				String id = job.getID();
				PicoJobsPlugin.getInstance().jobs.remove(id);
				FileCreator.getJobsConfig().getConfigurationSection("jobs").set(id, null);
				try {
					FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				p.sendMessage(LanguageManager.formatMessage("&cThe job " + id + " was deleted sucefully."));
				p.closeInventory();
				return;
			}
			}
			
			return;
		}
		
		/*
		 * Job Edit Click
		 */
		if(JobSettingsMenu.jobEditInventories.containsKey(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = JobSettingsMenu.jobEditInventories.get(e.getInventory());
			
			switch(e.getSlot()) {
			case(11): {
				String message = "\n§aSend the new displayname on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETDISPLAYNAME, job));
				p.closeInventory();
				return;
			}
			case(15): {
				String message = "\n§aSend the new salary on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETSALARY, job));
				p.closeInventory();
				return;
			}
			case(19): {
				String message = "\n§aSend the new job type on the chat in order to change it";
				new FancyMessage(message)
				.then(" §a( §nWiki Page§r§a )")
				.link("https://github.com/Picono435/PicoJobs/wiki/Types-of-jobs")
				.tooltip("§8Click here to acess the WIKI PAGE about this action.")
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETJOBTYPE, job));
				p.closeInventory();
				return;
			}
			case(20): {
				String message = "\n§aSend the new economy type on the chat in order to change it";
				new FancyMessage(message)
				.then(" §a( §nWiki Page§r§a )")
				.link("https://github.com/Picono435/PicoJobs/wiki/Economy-Types")
				.tooltip("§8Click here to acess the WIKI PAGE about this action.")
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETECONOMY, job));
				p.closeInventory();
				return;
			}
			case(24): {
				String message = "\n§aSend the new method on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETREQMETHOD, job));
				p.closeInventory();
				return;
			}
			case(33): {
				String message = "\n§aSend the new salary frequency on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETSALARYFREQ, job));
				p.closeInventory();
				return;
			}
			case(37): {
				String message = "\n§aSend the formatted whitelist like this: To add something put +:THING, to remove something put -:THING. If you want to add multiple things separate them with a comma and da space(, ).";
				new FancyMessage(message)
				.tooltip("§8EXAMPLE: +:BRICKS, +:LEAVES, -:OAK_WOOD")
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETWHITELIST, job));
				p.closeInventory();
				return;
			}
			case(38): {
				if(job.isWhitelist()) {
					job.setWhitelistType(false);
				} else {
					job.setWhitelistType(true);
				}
				JobSettingsMenu.openJobEdit(p, job);
			}
			case(39): {
				if(job.requiresPermission()) {
					job.setRequiresPermission(false);
				} else {
					job.setRequiresPermission(true);
				}
				JobSettingsMenu.openJobEdit(p, job);
				return;
			}
			case(42): {
				String message = "\n§aSend the new method frequency on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuActions.put(p, new ActionMenu(p, ActionEnum.SETMETHODFREQ, job));
				p.closeInventory();
				return;
			}
			}
			return;
		}
	}
	
	@EventHandler(ignoreCancelled = false)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(!menuActions.containsKey(p)) return;
		e.setCancelled(true);
		ActionMenu actionMenu = menuActions.get(p);
		Job job = (Job)actionMenu.getValues().get(0);
		ActionEnum action = actionMenu.getAction();
		if(action == ActionEnum.SETDISPLAYNAME) {
			job.setDisplayName(e.getMessage());
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETSALARY) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setSalary(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETJOBTYPE) {
			Type type = Type.getType(e.getMessage());
			if(type == null) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setType(type);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETECONOMY) {
			EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(e.getMessage());
			if(economy == null) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setEconomy(economy.getName());
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETREQMETHOD) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setMethod(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETSALARYFREQ) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setSalaryFrequency(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETWHITELIST) {
			String[] values = e.getMessage().split(", ");
			List<String> newWhitelist = new ArrayList<String>(job.getStringWhitelist());
			for(String value : values) {
				String noFormat = value.replaceFirst("\\+:", "").replaceFirst("\\-:", "");
				WhitelistConf whitelistConf = PicoJobsAPI.getJobsManager().getConfigWhitelist(job.getType());
				if(whitelistConf == WhitelistConf.MATERIAL) {
					if(Material.matchMaterial(noFormat) == null) {
						p.sendMessage(LanguageManager.formatMessage("&cERROR: We could not found any material with the name " + noFormat + ". Continuing to the next value..."));
						continue;
					}
				} else if(whitelistConf == WhitelistConf.ENTITY) {
					if(OtherUtils.getEntityByName(noFormat) == null) {
						p.sendMessage(LanguageManager.formatMessage("&cERROR: We could not found any entity with the name " + noFormat + ". Continuing to the next value..."));
						continue;
					}
				} else if(whitelistConf == WhitelistConf.JOB) {
					if(PicoJobsAPI.getJobsManager().getJob(noFormat) == null) {
						p.sendMessage(LanguageManager.formatMessage("&cERROR: We could not found any job with the ID " + noFormat + ". Continuing to the next value..."));
						continue;
					}
				}
				
				if(value.substring(0, 2).equals("+:")) {
					if(newWhitelist.contains(noFormat)) {
						p.sendMessage(LanguageManager.formatMessage("&cERROR: The value " + noFormat + " is already in the whitelist. Continuing to the next value..."));
						continue;
					}
					newWhitelist.add(noFormat);
					continue;
				}
				if(value.substring(0, 2).equals("-:")) {
					if(!newWhitelist.contains(noFormat)) {
						p.sendMessage(LanguageManager.formatMessage("&cERROR: The value " + noFormat + " is not in the whitelist. Continuing to the next value..."));
						continue;
					}
					newWhitelist.remove(noFormat);
					continue;
				}
				p.sendMessage(LanguageManager.formatMessage("&cERROR: Undefined prefix &8 " + value.substring(0, 2) + "&c, please use +: to add & -: to remove. Continuing to the next value..."));
				continue;
			}
			job.setWhitelist(newWhitelist);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == ActionEnum.SETMETHODFREQ) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				return;
			}
			job.setMethodFrequency(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			new BukkitRunnable() {
				public void run() {
					JobSettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
	}
	
}
