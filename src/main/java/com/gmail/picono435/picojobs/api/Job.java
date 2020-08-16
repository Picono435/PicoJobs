package com.gmail.picono435.picojobs.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class Job {
	
	private String name;
	private String displayname;
	private String tag;
	private Type type;
	private double method;
	private double salary;
	private boolean requiresPermission;
	
	// GUI SETTINGS
	private int slot;
	private Material item;
	private int itemData;
	private boolean enchanted;
	
	// OPTIONAL
	private String killJob;
	
	public Job(String name, String displayname, String tag, Type type, double method, double salary, boolean requiresPermission, int slot, String item, int itemData, boolean enchanted, String killJob) {
		this.name = name;
		this.displayname = displayname;
		this.tag = tag;
		this.type = type;
		this.method = method;
		this.salary = salary;
		this.requiresPermission = requiresPermission;
		
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
		
		this.killJob = killJob;
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
	
	public double getMethod() {
		return method;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public boolean requiresPermission() {
		return requiresPermission;
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
			int itemData = getItemData() - 1;
			if(itemData == -1) {
				builder = new ItemBuilder(getMaterial());
			} else {
				builder = new ItemBuilder(getMaterial(), 1, (byte)itemData);
			}
		} else {
			builder = new ItemBuilder(getMaterial());
		}
		builder.setName(getDisplayName());
		if(isEnchanted()) builder.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		builder.removeAttributes();
		return builder.toItemStack();
	}
	
	public String getKillJob() {
		return killJob;
	}
}
