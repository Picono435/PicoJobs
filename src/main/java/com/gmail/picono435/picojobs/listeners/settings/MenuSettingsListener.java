package com.gmail.picono435.picojobs.listeners.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.menu.ActionEnum;
import com.gmail.picono435.picojobs.menu.ActionMenu;
import com.gmail.picono435.picojobs.menu.GUISettingsMenu;
import com.gmail.picono435.picojobs.utils.FileCreator;

import mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.ChatColor;

public class MenuSettingsListener implements Listener {
	
	private static Map<Player, ActionMenu> actions = new HashMap<Player, ActionMenu>();
	
	@EventHandler()
	public void onGUISettingsClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if(!p.hasPermission("picojobs.admin")) return;
		
		/*
		 * General GUI Settings Click
		 */
		if(GUISettingsMenu.generalInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			switch(e.getSlot()) {
			case(11): {
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "Editing the Choose Job GUI in-game is not yet implemented. Please go into plugins/PicoJobs/settings/jobs.yml in order to edit it.");
				//GUISettingsMenu.openChooseJobSettings(p);
				return;
			}
			case(13): {
				p.closeInventory();
				GUISettingsMenu.openNeedWorkSettings(p);
				return;
			}
			case(15): {
				p.closeInventory();
				GUISettingsMenu.openHasWorkSettings(p);
				return;
			}
			}
			return;
		}
		
		/*
		 * Item Edit GUI Settings Click
		 */
		if(GUISettingsMenu.itemEdit.containsKey(e.getInventory())) {
			e.setCancelled(true);
			ItemVariable var = GUISettingsMenu.itemEdit.get(e.getInventory());
			//ItemStack item = var.getItem();
			//String gui = var.getGUI();
			ConfigurationSection itemSettings = var.getItemSettings();
			
			switch(e.getSlot()) {
			case(10): {
				// RENAME
				String message = "\n§aSend the new name for the item in the chat";
				p.sendMessage(message);
				actions.put(p, new ActionMenu(p, ActionEnum.RENAMEITEM, var));
				return;
			}
			case(12): {
				// LORE EDITOR
				String message = "\n§aSend the new lore for the item in the chat separated by commas (,)";
				p.sendMessage(message);
				actions.put(p, new ActionMenu(p, ActionEnum.SETLOREITEM, var));
				return;
			}
			case(14): {
				// ENCHANTED
				boolean enchanted = itemSettings.getBoolean("enchanted");
				boolean newEnchanted = false;
				if(enchanted) {
					newEnchanted = false;
				} else {
					newEnchanted = true;
				}
				itemSettings.set("enchanted", newEnchanted);
				try {
					FileCreator.getGUI().save(FileCreator.getGUIFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				p.closeInventory();
				GUISettingsMenu.openGeneral(p);
				return;
			}
			case(16): {
				// ACTION
				String message = "\n§aSend the new item action in the chat";
				new FancyMessage(message)
				.then(" §a( §nWiki Page§r§a )")
				.link("https://github.com/Picono435/PicoJobs/wiki/Editing-GUIs#items-configuration-section--action")
				.tooltip("§8Click here to acess the WIKI PAGE about this action.")
				.then("\n§a")
				.send(p);
				p.sendMessage(message);
				actions.put(p, new ActionMenu(p, ActionEnum.SETACTIONITEM, var));
				return;
			}
			}
			return;
		}
		
		/*
		 * Need Work GUI Settings Click
		 */
		if(GUISettingsMenu.guiSettings.containsKey(e.getInventory()) && GUISettingsMenu.guiSettings.get(e.getInventory()).equals("need-work")) {
			if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				e.setCancelled(true);
				p.closeInventory();
				GUISettingsMenu.openItemEdit(p, e.getCurrentItem(), "need-work", StringUtils.substringBetween(e.getCurrentItem().getItemMeta().getDisplayName(), "[[", "]]"));
				return;
			}
			return;
		}
		
		/*
		 * Has Work GUI Settings Click
		 */
		if(GUISettingsMenu.guiSettings.containsKey(e.getInventory()) && GUISettingsMenu.guiSettings.get(e.getInventory()).equals("has-work")) {
			if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				e.setCancelled(true);
				p.closeInventory();
				GUISettingsMenu.openItemEdit(p, e.getCurrentItem(), "has-work", StringUtils.substringBetween(e.getCurrentItem().getItemMeta().getDisplayName(), "[[", "]]"));
				return;
			}
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer() == null) return;
		Player p = (Player) e.getPlayer();
		if(!GUISettingsMenu.guiSettings.containsKey(e.getInventory())) return;
		String gui = GUISettingsMenu.guiSettings.get(e.getInventory());
		
		int slot = 0;
		List<String> settings = new ArrayList<String>();
		for(ItemStack item : e.getInventory().getContents()) {
			slot++;
			if(item == null || item.getType() == Material.AIR) continue;
			String setting = StringUtils.substringBetween(item.getItemMeta().getDisplayName(), "[[", "]]");
			if(setting == null) {
				if(item.getItemMeta().hasDisplayName()) {
					setting = item.getItemMeta().getDisplayName().toLowerCase().replace("[^a-zA-Z0-9]", "").substring(0, 10);
				} else {
					setting = item.getType().name().toLowerCase();
				}
			}
			settings.add(setting);
			ConfigurationSection itemSettings = FileCreator.getGUI().getConfigurationSection("gui-settings").getConfigurationSection(gui).getConfigurationSection("items").getConfigurationSection(setting);

			if(itemSettings == null) {
				itemSettings = FileCreator.getGUI().getConfigurationSection("gui-settings").getConfigurationSection(gui).getConfigurationSection("items").createSection(setting);
				if(item.getItemMeta().hasDisplayName()) {
					itemSettings.set("name", item.getItemMeta().getDisplayName().replace("§", "&"));
				} else {
					itemSettings.set("name", "&cUnknow displayname");
				}
				itemSettings.set("lore", item.getItemMeta().getLore());
				itemSettings.set("action", "none");
				itemSettings.set("material", item.getType().name());
				if(PicoJobsPlugin.getInstance().isLegacy()) {
					itemSettings.set("item-data", item.getDurability());
				}
				itemSettings.set("enchanted", !item.getEnchantments().isEmpty());
			}
			
			itemSettings.set("slot", slot);
			continue;
		}
		
		ConfigurationSection it = FileCreator.getGUI().getConfigurationSection("gui-settings").getConfigurationSection(gui).getConfigurationSection("items");
		for(String value : it.getValues(false).keySet()) {
			if(!settings.contains(value)) {
				it.set(value, null);
				continue;
			} else {
				continue;
			}
		}
		
		try {
			FileCreator.getGUI().save(FileCreator.getGUIFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		p.sendMessage(LanguageManager.getMessage("sucefully", p));
		
		if(GUISettingsMenu.generalInventories.contains(e.getInventory())) GUISettingsMenu.generalInventories.remove(e.getInventory());
		if(GUISettingsMenu.guiSettings.containsKey(e.getInventory())) GUISettingsMenu.guiSettings.remove(e.getInventory());
		if(GUISettingsMenu.itemEdit.containsKey(e.getInventory())) GUISettingsMenu.itemEdit.remove(e.getInventory());
	}
	
	@EventHandler()
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(!actions.containsKey(p)) return;
		e.setCancelled(true);
		ActionMenu actionMenu = actions.get(p);
		ItemVariable itemVariable = (ItemVariable)actionMenu.getValues().get(0);
		ConfigurationSection itemSettings = itemVariable.getItemSettings();
		ActionEnum action = actionMenu.getAction();
		
		if(action == ActionEnum.RENAMEITEM) {
			itemSettings.set("name", e.getMessage());
			try {
				FileCreator.getGUI().save(FileCreator.getGUIFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			actions.remove(p);
			new BukkitRunnable() {
				public void run() {
					GUISettingsMenu.openGeneral(p);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		
		if(action == ActionEnum.SETLOREITEM) {
			itemSettings.set("lore", e.getMessage().split(","));
			try {
				FileCreator.getGUI().save(FileCreator.getGUIFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			actions.remove(p);
			new BukkitRunnable() {
				public void run() {
					GUISettingsMenu.openGeneral(p);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
		
		if(action == ActionEnum.SETACTIONITEM) {
			itemSettings.set("action", e.getMessage().replace(" ", "-").toLowerCase());
			try {
				FileCreator.getGUI().save(FileCreator.getGUIFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			p.sendMessage(LanguageManager.getMessage("sucefully", p));
			actions.remove(p);
			new BukkitRunnable() {
				public void run() {
					GUISettingsMenu.openGeneral(p);
				}
			}.runTask(PicoJobsPlugin.getInstance());
		}
	}
}
