package com.gmail.picono435.picojobs.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.listeners.ClickInventoryListener;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

public class JobsMenu {
	
	public static void openMenu(Player p) {
		ConfigurationSection guiSettings = FileCreator.getGUI().getConfigurationSection("gui-settings");
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(jp.hasJob()) {
			if(jp.isWorking()) {
				p.openInventory(getWorkStatusJobMenu(guiSettings, p, false));
			} else {
				p.openInventory(getNeedAcceptJobMenu(guiSettings, p, false));
			}
		} else {
			p.openInventory(getChooseJobMenu(guiSettings));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Inventory getChooseJobMenu(ConfigurationSection guiSettings) {
		ConfigurationSection category = guiSettings.getConfigurationSection("choose-job");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		for(Job job : PicoJobsAPI.getJobsManager().getJobs()) {
			inv.setItem(job.getSlot(), job.getFormattedItem());
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.getInstance().isLegacy()) {
				int itemData = category.getInt("item-data");
				if(itemData == -1) {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
				} else {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")), 1, (byte)itemData);
				}
			} else {
				builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
			}
			if(category.getBoolean("enchanted")) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			builder.setName(ChatColor.translateAlternateColorCodes('&', category.getString("item-name")));
			builder.removeAttributes();
			for(int i = 0; i < inv.getSize(); i++) {
				if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
					inv.setItem(i, builder.toItemStack());
				}
			}
		}
		return inv;
	}
	
	@SuppressWarnings("deprecation")
	public static Inventory getNeedAcceptJobMenu(ConfigurationSection guiSettings, Player p, boolean toEdit) {
		ConfigurationSection category = guiSettings.getConfigurationSection("need-work");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		ConfigurationSection items = category.getConfigurationSection("items");
		
		for(String itemName : items.getKeys(false)) {
			ConfigurationSection itemConfig = items.getConfigurationSection(itemName);
			ItemBuilder builder;
			if(PicoJobsPlugin.getInstance().isLegacy()) {
				int itemData = itemConfig.getInt("item-data");
				if(itemData == -1) {
					builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")));
				} else {
					builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")), 1, (byte)itemData);
				}
			} else {
				builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")));
			}
			builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name")));
			if(toEdit) {
				builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name").replace("[[", "")) + " [[" + itemName + "]]");
			}
			
			if(itemConfig.getBoolean("enchanted")) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			
			builder.removeAttributes();
			
			List<String> lore = itemConfig.getStringList("lore");
			lore = lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
			lore = PlaceholderAPIHook.setPlaceholders(p, lore);
			builder.setLore(lore);
			
			ItemStack item = builder.toItemStack();
			
			ClickInventoryListener.actionItems.put(item, itemConfig.getString("action"));
			
			inv.setItem(itemConfig.getInt("slot") - 1, item);
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.getInstance().isLegacy()) {
				int itemData = category.getInt("item-data");
				if(itemData == -1) {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
				} else {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")), 1, (byte)itemData);
				}
			} else {
				builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
			}
			if(category.getBoolean("enchanted")) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			builder.setName(ChatColor.translateAlternateColorCodes('&', category.getString("item-name")));
			builder.removeAttributes();
			for(int i = 0; i < inv.getSize(); i++) {
				if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
					inv.setItem(i, builder.toItemStack());
				}
			}
		}
		return inv;
	}
	
	@SuppressWarnings("deprecation")
	public static Inventory getWorkStatusJobMenu(ConfigurationSection guiSettings, Player p, boolean toEdit) {
		ConfigurationSection category = guiSettings.getConfigurationSection("has-work");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		ConfigurationSection items = category.getConfigurationSection("items");
		
		for(String itemName : items.getKeys(false)) {
			ConfigurationSection itemConfig = items.getConfigurationSection(itemName);
			ItemBuilder builder;
			if(PicoJobsPlugin.getInstance().isLegacy()) {
				int itemData = itemConfig.getInt("item-data");
				if(itemData == -1) {
					builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")));
				} else {
					builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")), 1, (byte)itemData);
				}
			} else {
				builder = new ItemBuilder(Material.matchMaterial(itemConfig.getString("material")));
			}
			builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name")));
			if(toEdit) {
				builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name").replace("[[", "")) + " [[" + itemName + "]]");
			}
			
			if(itemConfig.getBoolean("enchanted")) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			
			builder.removeAttributes();
			
			List<String> lore = itemConfig.getStringList("lore");
			lore = lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
			lore = PlaceholderAPIHook.setPlaceholders(p, lore);
			builder.setLore(lore);
			
			ItemStack item = builder.toItemStack();
			
			ClickInventoryListener.actionItems.put(item, itemConfig.getString("action"));
			
			inv.setItem(itemConfig.getInt("slot") - 1, item);
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.getInstance().isLegacy()) {
				int itemData = category.getInt("item-data");
				if(itemData == -1) {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
				} else {
					builder = new ItemBuilder(Material.matchMaterial(category.getString("item")), 1, (byte)itemData);
				}
			} else {
				builder = new ItemBuilder(Material.matchMaterial(category.getString("item")));
			}
			if(category.getBoolean("enchanted")) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			builder.setName(ChatColor.translateAlternateColorCodes('&', category.getString("item-name")));
			builder.removeAttributes();
			for(int i = 0; i < inv.getSize(); i++) {
				if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
					inv.setItem(i, builder.toItemStack());
				}
			}
		}
		return inv;
	}
}
