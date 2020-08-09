package com.gmail.picono435.picojobs.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
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
import com.gmail.picono435.picojobs.listeners.ClickInventoryListener;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class JobsMenu {
	
	public static void openMenu(Player p) {
		ConfigurationSection guiSettings = FileCreator.getGUI().getConfigurationSection("gui-settings");
		JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
		if(jp.hasJob()) {
			if(jp.isWorking()) {
				p.openInventory(getWorkStatusJobMenu(guiSettings, p));
			} else {
				p.openInventory(getNeedAcceptJobMenu(guiSettings, p));
			}
		} else {
			p.openInventory(getChooseJobMenu(guiSettings));
		}
	}
	
	@SuppressWarnings("deprecation")
	private static Inventory getChooseJobMenu(ConfigurationSection guiSettings) {
		ConfigurationSection category = guiSettings.getConfigurationSection("choose-job");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		for(Job job : PicoJobsAPI.getJobsManager().getJobs()) {
			inv.setItem(job.getSlot(), job.getFormattedItem());
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.isLegacy()) {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()), 1, (byte)category.getInt("item-data"));
			} else {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()));
			}
			if(category.getBoolean("enchanted")) builder.addEnchant(Enchantment.ARROW_DAMAGE, 1);
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
	private static Inventory getNeedAcceptJobMenu(ConfigurationSection guiSettings, Player p) {
		ConfigurationSection category = guiSettings.getConfigurationSection("need-work");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		ConfigurationSection items = category.getConfigurationSection("items");
		
		for(String itemName : items.getKeys(false)) {
			ConfigurationSection itemConfig = items.getConfigurationSection(itemName);
			ItemBuilder builder;
			if(PicoJobsPlugin.isLegacy()) {
				builder = new ItemBuilder(Material.getMaterial(itemConfig.getString("material").toUpperCase()), 1, (byte)category.getInt("item-data"));
			} else {
				builder = new ItemBuilder(Material.getMaterial(itemConfig.getString("material").toUpperCase()));
			}
			builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name")));
			
			if(itemConfig.getBoolean("enchanted")) builder.addEnchant(Enchantment.ARROW_DAMAGE, 1);
			
			builder.removeAttributes();
			
			List<String> lore = itemConfig.getStringList("lore");
			lore = lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
			if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				lore = PlaceholderAPI.setPlaceholders(p, lore);
			}
			builder.setLore(lore);
			
			ItemStack item = builder.toItemStack();
			
			ClickInventoryListener.actionItems.put(item, itemConfig.getString("action"));
			
			inv.setItem(itemConfig.getInt("slot") + 1, item);
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.isLegacy()) {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()), 1, (byte)category.getInt("item-data"));
			} else {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()));
			}
			if(category.getBoolean("enchanted")) builder.addEnchant(Enchantment.ARROW_DAMAGE, 1);
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
	private static Inventory getWorkStatusJobMenu(ConfigurationSection guiSettings, Player p) {
		ConfigurationSection category = guiSettings.getConfigurationSection("has-work");
		Inventory inv = Bukkit.createInventory(null, category.getInt("size"), category.getString("title"));
		
		ConfigurationSection items = category.getConfigurationSection("items");
		
		for(String itemName : items.getKeys(false)) {
			ConfigurationSection itemConfig = items.getConfigurationSection(itemName);
			ItemBuilder builder;
			if(PicoJobsPlugin.isLegacy()) {
				builder = new ItemBuilder(Material.getMaterial(itemConfig.getString("material").toUpperCase()), 1, (byte)category.getInt("item-data"));
			} else {
				builder = new ItemBuilder(Material.getMaterial(itemConfig.getString("material").toUpperCase()));
			}
			builder.setName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name")));
			
			if(itemConfig.getBoolean("enchanted")) builder.addEnchant(Enchantment.ARROW_DAMAGE, 1);
			
			builder.removeAttributes();
			
			List<String> lore = itemConfig.getStringList("lore");
			lore = lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
			if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				lore = PlaceholderAPI.setPlaceholders(p, lore);
			}
			builder.setLore(lore);
			
			ItemStack item = builder.toItemStack();
			
			ClickInventoryListener.actionItems.put(item, itemConfig.getString("action"));
			
			inv.setItem(itemConfig.getInt("slot") + 1, item);
		}
		
		if(category.getBoolean("put-background-item")) {
			ItemBuilder builder;
			if(PicoJobsPlugin.isLegacy()) {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()), 1, (byte)category.getInt("item-data"));
			} else {
				builder = new ItemBuilder(Material.getMaterial(category.getString("item").toUpperCase()));
			}
			if(category.getBoolean("enchanted")) builder.addEnchant(Enchantment.ARROW_DAMAGE, 1);
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
