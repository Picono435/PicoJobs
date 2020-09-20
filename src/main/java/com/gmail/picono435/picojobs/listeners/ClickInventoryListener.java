package com.gmail.picono435.picojobs.listeners;

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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.WhitelistConf;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.MenuAction;
import com.gmail.picono435.picojobs.menu.SettingsMenu;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.OtherUtils;

import mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.ChatColor;

public class ClickInventoryListener implements Listener {
	
	public static Map<ItemStack, String> actionItems = new HashMap<ItemStack, String>();
	
	private static Map<Player, MenuAction> menuActions  = new HashMap<Player, MenuAction>();
	private static Map<Player, Job> menuJobs  = new HashMap<Player, Job>();
	
	@EventHandler()
	public void onBasicClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		/*
		 * Choose Jobs Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.choose-job.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
			if(job.requiresPermission() && !p.hasPermission("picojobs.job." + job.getID())) {
				p.sendMessage(LanguageManager.getMessage("no-permission", p));
				return;
			}
			jp.setJob(job);
			p.sendMessage(LanguageManager.getMessage("choosed-job", p));
			p.closeInventory();
			return;
		}
		
		/*
		 * Accept Work Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.need-work.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			//Job job = jp.getJob();
			String action = actionItems.get(e.getCurrentItem());
			if(action.equalsIgnoreCase("salary")) {
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					p.closeInventory();
					return;
				}
				String economyString = jp.getJob().getEconomy();
				if(!PicoJobsPlugin.getInstance().economies.containsKey(economyString)) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation said. Please contact an administrator for get more information."));
					p.closeInventory();
					return;
				}
				EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(economyString);
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				economy.deposit(p, salary);
				jp.removeSalary(salary);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("acceptwork")) {
				p.sendMessage(LanguageManager.getMessage("accepted-work", p));
				jp.setWorking(true);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("leavejob")) {
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				p.closeInventory();
				return;
			}
			return;
		}
		
		/*
		 * Status Work Menu Clicking Event
		 */
		if(e.getView().getTitle().equals(FileCreator.getGUI().getString("gui-settings.has-work.title"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
			//Job job = jp.getJob();
			String action = actionItems.get(e.getCurrentItem());
			if(action.equalsIgnoreCase("salary")) {
				double salary = jp.getSalary();
				if(salary <= 0) {
					p.sendMessage(LanguageManager.getMessage("no-salary", p));
					p.closeInventory();
					return;
				}
				String economyString = jp.getJob().getEconomy();
				if(!PicoJobsPlugin.getInstance().economies.containsKey(economyString)) {
					p.sendMessage(LanguageManager.formatMessage("&cWe did not find the economy implementation said. Please contact an administrator for get more information."));
					p.closeInventory();
					return;
				}
				EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(economyString);
				p.sendMessage(LanguageManager.getMessage("got-salary", p));
				economy.deposit(p, salary);
				jp.removeSalary(salary);
				p.closeInventory();
				return;
			}
			if(action.equalsIgnoreCase("leavejob")) {
				jp.removePlayerStats();
				p.sendMessage(LanguageManager.getMessage("left-job", p));
				p.closeInventory();
				return;
			}
			return;
		}
	}

	@EventHandler()
	public void onSettingsClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if(!p.hasPermission("picojobs.admin")) return;
		
		/*
		 * General Settings Click
		 */
		if(SettingsMenu.generalInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			switch(e.getSlot()) {
			case(13): {
				p.closeInventory();
				SettingsMenu.openJobsList(p);
			}
			}
			return;
		}
		
		/*
		 * Job List Settings Click
		 */
		if(SettingsMenu.jobListInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = PicoJobsAPI.getJobsManager().getJobByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
			
			p.closeInventory();
			SettingsMenu.openJobSettings(p, job);
			return;
		}
		
		/*
		 * Job Settings Click
		 */
		if(SettingsMenu.jobSettingsInventories.containsKey(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = SettingsMenu.jobSettingsInventories.get(e.getInventory());
			
			switch(e.getSlot()) {
			case(13): {
				p.closeInventory();
				SettingsMenu.openJobEdit(p, job);
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
		if(SettingsMenu.jobEditInventories.containsKey(e.getInventory())) {
			e.setCancelled(true);
			
			Job job = SettingsMenu.jobEditInventories.get(e.getInventory());
			
			/*
			 * 10 - ID
			 * 11 - DISPLAYNAME
			 * 15 - SALARY
			 * 19 - JOB TYPE
			 * 20 - ECONOMY IMPLEMENTATION
			 * 24 - REQUIRED METHOD
			 * 33 - SALARY FREQUENCY
			 * 37 - WHITELIST/BLACKLIST
			 * 38 - WHITELIST TYPE
			 * 39 - GUI SETTINGS
			 * 40 - REQUIRES PERMISSION
			 * 42 - METHOD FREQUENCY
			 */
			
			switch(e.getSlot()) {
			case(11): {
				String message = "\n§aSend the new displayname on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETDISPLAYNAME);
				p.closeInventory();
				return;
			}
			case(15): {
				String message = "\n§aSend the new salary on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETSALARY);
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
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETJOBTYPE);
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
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETECONOMY);
				p.closeInventory();
				return;
			}
			case(24): {
				String message = "\n§aSend the new method on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETREQMETHOD);
				p.closeInventory();
				return;
			}
			case(33): {
				String message = "\n§aSend the new salary frequency on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETSALARYFREQ);
				p.closeInventory();
				return;
			}
			case(37): {
				String message = "\n§aSend the formatted whitelist like this: To add something put +:THING, to remove something put -:THING. If you want to add multiple things separate them with a comma and da space(, ).";
				new FancyMessage(message)
				.tooltip("§8EXAMPLE: +:BRICKS, +:LEAVES, -:OAK_WOOD")
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETWHITELIST);
				p.closeInventory();
				return;
			}
			case(38): {
				if(job.isWhitelist()) {
					job.setWhitelistType(false);
				} else {
					job.setWhitelistType(true);
				}
				SettingsMenu.openJobEdit(p, job);
			}
			case(39): {
				p.sendMessage(ChatColor.RED + "This feature is not added yet. For more information check our discord. PicoJobs v" + PicoJobsPlugin.getInstance().getDescription().getVersion());
			}
			case(40): {
				if(job.requiresPermission()) {
					job.setRequiresPermission(false);
				} else {
					job.setRequiresPermission(true);
				}
				SettingsMenu.openJobEdit(p, job);
				return;
			}
			case(42): {
				String message = "\n§aSend the new method frequency on the chat in order to change it";
				new FancyMessage(message)
				.then("\n§a")
				.send(p);
				if(menuJobs.containsKey(p)) menuJobs.remove(p);
				if(menuActions.containsKey(p)) menuActions.remove(p);
				menuJobs.put(p, job);
				menuActions.put(p, MenuAction.SETMETHODFREQ);
				p.closeInventory();
				return;
			}
			}
			return;
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(!menuActions.containsKey(p)) return;
		if(!menuJobs.containsKey(p)) return;
		e.setCancelled(true);
		Job job = menuJobs.get(p);
		MenuAction action = menuActions.get(p);
		if(action == MenuAction.SETDISPLAYNAME) {
			job.setDisplayName(e.getMessage());
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETSALARY) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setSalary(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETJOBTYPE) {
			Type type = Type.getType(e.getMessage());
			if(type == null) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setType(type);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETECONOMY) {
			EconomyImplementation economy = PicoJobsPlugin.getInstance().economies.get(e.getMessage());
			if(economy == null) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setEconomy(economy.getName());
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETREQMETHOD) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setMethod(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETSALARYFREQ) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setSalaryFrequency(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETWHITELIST) {
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
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		if(action == MenuAction.SETMETHODFREQ) {
			double value = 0;
			try {
				value = Double.parseDouble(e.getMessage());
			} catch(Exception ex) {
				p.sendMessage(LanguageManager.getMessage("invalid-arg", p));
				menuActions.remove(p);
				menuJobs.remove(p);
				return;
			}
			job.setMethodFrequency(value);
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			menuActions.remove(p);
			menuJobs.remove(p);
			new BukkitRunnable() {
				public void run() {
					SettingsMenu.openJobEdit(p, job);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(SettingsMenu.generalInventories.contains(e.getInventory())) SettingsMenu.generalInventories.remove(e.getInventory());
		if(SettingsMenu.jobListInventories.contains(e.getInventory())) SettingsMenu.jobListInventories.remove(e.getInventory());
		if(SettingsMenu.jobEditInventories.containsKey(e.getInventory())) SettingsMenu.jobEditInventories.remove(e.getInventory());
		if(SettingsMenu.jobSettingsInventories.containsKey(e.getInventory())) SettingsMenu.jobSettingsInventories.remove(e.getInventory());
	}
}
