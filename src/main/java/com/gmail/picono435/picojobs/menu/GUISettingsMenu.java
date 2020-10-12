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

import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class GUISettingsMenu {
	
	public static List<Inventory> generalInventories = new ArrayList<Inventory>();
	public static Map<Inventory, String> guiSettings = new HashMap<Inventory, String>();
	public static Map<Inventory, ItemStack> itemEdit = new HashMap<Inventory, ItemStack>();
	
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
		
		inv.setContents(JobsMenu.getChooseJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings")).getContents());
		
		p.openInventory(inv);
		guiSettings.put(inv, "need-work");
	}
	
	public static void openHasWorkSettings(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - [Right-Click Edit Item] [Left-Click Move/Change Item]");
		
		JobsMenu.getChooseJobMenu(FileCreator.getGUI().getConfigurationSection("gui-settings"));
		
		p.openInventory(inv);
		guiSettings.put(inv, "has-work");
	}
	
	public static void openItemEdit(Player p, ItemStack item, String gui) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - Item Edit [" + gui + "]");
		
		// NAME
		// LORE
		// ENCHANTED
		// ACTION
		
		inv.setItem(4, item);
		/*inv.setItem(9, new ItemBuilder(Material.MAP));
		inv.setItem(9, item);
		inv.setItem(9, item);
		inv.setItem(9, item);*/
		
		p.openInventory(inv);
	}
}
