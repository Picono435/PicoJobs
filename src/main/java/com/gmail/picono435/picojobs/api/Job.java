package com.gmail.picono435.picojobs.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class Job {
	
	private String name;
	private String displayname;
	private String tag;
	private Type type;
	private double reqmethod;
	
	// GUI SETTINGS
	private int slot;
	private Material item;
	private int itemData;
	private boolean enchanted;
	
	public Job(String name, String displayname, String tag, Type type, double reqmethod, int slot, String item, int itemData, boolean enchanted) {
		this.name = name;
		this.displayname = displayname;
		this.tag = tag;
		this.type = type;
		this.reqmethod = reqmethod;
		this.slot = slot;
		Material m;
		if(PicoJobsPlugin.isLegacy()) {
			m = Material.getMaterial(item.toUpperCase());
		} else {
			m = Material.getMaterial(item.toUpperCase());
		}
		this.item = m;
		this.itemData = itemData;
		this.enchanted = enchanted;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return ChatColor.translateAlternateColorCodes('&', displayname);
	}
	
	public String getTag() {
		return ChatColor.translateAlternateColorCodes('&', tag);
	}
	
	public Type getType() {
		return type;
	}
	
	public double getRequiredMethod() {
		return reqmethod;
	}
	
	public int getSlot() {
		return slot - 1;
	}
	
	public Material getMaterial() {
		return item;
	}
	
	public int getItemData() {
		return itemData;
	}
	
	public boolean isEnchanted() {
		return enchanted;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getFormattedItem() {
		ItemBuilder builder;
		if(PicoJobsPlugin.isLegacy()) {
			builder = new ItemBuilder(getMaterial(), 1, (byte)getItemData());
		} else {
			builder = new ItemBuilder(getMaterial());
		}
		builder.setName(getDisplayName());
		return builder.toItemStack();
	}
}
