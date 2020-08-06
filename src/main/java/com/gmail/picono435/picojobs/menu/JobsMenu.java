package com.gmail.picono435.picojobs.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.utils.ItemBuilder;
import com.gmail.picono435.picojobs.vars.Job;

import net.md_5.bungee.api.ChatColor;

public class JobsMenu {
	
	public static void openMenu(Player p) {
		ConfigurationSection guiSettings = PicoJobsPlugin.getPlugin().getConfig().getConfigurationSection("gui-settings");
		if(PicoJobsAPI.getPlayersManager().getJobPlayer(p).hasJob()) {
			p.sendMessage("LoL");
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
