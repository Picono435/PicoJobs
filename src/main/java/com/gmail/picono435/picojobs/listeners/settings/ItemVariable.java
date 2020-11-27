package com.gmail.picono435.picojobs.listeners.settings;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.utils.FileCreator;

public class ItemVariable {
	
	private ItemStack item;
	private String gui;
	private ConfigurationSection itemSetting;
	
	public ItemVariable(ItemStack item, String gui, String itemSetting) {
		this.item = item;
		this.gui = gui;
		this.itemSetting = FileCreator.getGUI().getConfigurationSection("gui-settings").getConfigurationSection(gui).getConfigurationSection("items").getConfigurationSection(itemSetting);
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public String getGUI() {
		return gui;
	}
	
	public ConfigurationSection getItemSettings() {
		return itemSetting;
	}
}
