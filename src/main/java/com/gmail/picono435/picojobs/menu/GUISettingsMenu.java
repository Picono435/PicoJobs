package com.gmail.picono435.picojobs.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.listeners.settings.ItemVariable;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class GUISettingsMenu {
	
	public static List<Inventory> generalInventories = new ArrayList<Inventory>();
	public static Map<Inventory, String> guiSettings = new HashMap<Inventory, String>();
	public static Map<Inventory, ItemVariable> itemEdit = new HashMap<Inventory, ItemVariable>();
	
	/*
	 * choose-job
	 * need-work
	 * has-work
	 */
	
	public static void openGeneral(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - Settings");
		
		inv.setItem(11, new ItemBuilder(Material.ENDER_CHEST).setName(ChatColor.AQUA + "Choose Job Menu").removeAttributes().toItemStack());
		inv.setItem(13, new ItemBuilder(Material.ENDER_CHEST).setName(ChatColor.AQUA + "Need Work Menu").removeAttributes().toItemStack());
		inv.setItem(15, new ItemBuilder(Material.ENDER_CHEST).setName(ChatColor.AQUA + "Has Work Menu").removeAttributes().toItemStack());
		
		p.openInventory(inv);
		generalInventories.add(inv);
	}
	
	public static void openChooseJobSettings(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, "PicoJobs - [Right-Click Edit Item] [Left-Click Move/Change Item]");

		inv.setContents(JobsMenu.getChooseJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings")).getContents());

		p.openInventory(inv);
		guiSettings.put(inv, "choose-job");
	}
	
	public static void openNeedWorkSettings(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - [Right-Click Edit Item] [Left-Click Move/Change Item]");
		
		inv.setContents(JobsMenu.getNeedAcceptJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings"), p, true).getContents());
		
		p.openInventory(inv);
		guiSettings.put(inv, "need-work");
	}
	
	public static void openHasWorkSettings(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - [Right-Click Edit Item] [Left-Click Move/Change Item]");
		
		JobsMenu.getChooseJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings"));
		
		inv.setContents(JobsMenu.getWorkStatusJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings"), p, true).getContents());
		
		p.openInventory(inv);
		guiSettings.put(inv, "has-work");
	}
	
	public static void openItemEdit(Player p, ItemStack item, String gui, String itemSetting) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - Item Edit");
		
		// NAME
		// LORE
		// ENCHANTED
		// ACTION
		
		inv.setItem(4, item);
		inv.setItem(10, new ItemBuilder(Material.MAP).setName(ChatColor.GREEN + "Rename").toItemStack());
		inv.setItem(12, new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Lore Editor").toItemStack());
		inv.setItem(14, new ItemBuilder(Material.BOOK).setName(ChatColor.GREEN + "Enchanted").toItemStack());
		if(item.getItemMeta().getEnchants().size() != 0) {
			inv.setItem(14, new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.GREEN + "Enchanted").toItemStack());
		}
		inv.setItem(16, new ItemBuilder(Material.LEVER).setName(ChatColor.YELLOW + "Action").toItemStack());
		
		p.openInventory(inv);
		itemEdit.put(inv, new ItemVariable(item, gui, itemSetting));
	}
}
