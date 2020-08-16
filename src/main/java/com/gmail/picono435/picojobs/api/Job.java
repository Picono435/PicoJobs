package com.gmail.picono435.picojobs.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

/**
 * Represents a job.
 * 
 * @author Picono435
 *
 */
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
	
	/**
	 * Gets the name of the job
	 * 
	 * @return the name of the job
	 * @author Picono435
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the display name of the job
	 * 
	 * @return the display name of the job
	 * @author Picono435
	 */
	public String getDisplayName() {
		return ChatColor.translateAlternateColorCodes('&', displayname);
	}
	
	/**
	 * Gets the tag of the job
	 * 
	 * @return the tag of the job
	 * @author Picono435
	 */
	public String getTag() {
		return ChatColor.translateAlternateColorCodes('&', tag);
	}
	
	/**
	 * Gets the type of the job
	 * 
	 * @return the type of the job
	 * @author Picono435
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Gets the method of the job
	 * 
	 * @return the method of the job
	 * @author Picono435
	 */
	public double getMethod() {
		return method;
	}
	
	/**
	 * Gets the salary of the job
	 * 
	 * @return the salary of the job
	 * @author Picono435
	 */
	public double getSalary() {
		return salary;
	}
	
	/**
	 * Check if the job requires permission to enter or not
	 * 
	 * @return true if it requires permission, false if not
	 * @author Picono435
	 */
	public boolean requiresPermission() {
		return requiresPermission;
	}
	
	/**
	 * Gets the item slot
	 * 
	 * @return the slot of the job item
	 * @author Picono435
	 */
	public int getSlot() {
		return slot - 1;
	}
	
	/**
	 * Gets the item material
	 * 
	 * @return the material of the job item
	 * @author Picono435
	 */
	public Material getMaterial() {
		return item;
	}
	
	/**
	 * Gets the item data slot
	 * 
	 * @deprecated
	 * @return the item data of the job item
	 * @author Picono435
	 */
	@Deprecated
	public int getItemData() {
		return itemData;
	}
	
	/**
	 * Checks if the job item is enchanted or not
	 * 
	 * @return true if the job item is enchanted, false if not
	 * @author Picono435
	 */
	public boolean isEnchanted() {
		return enchanted;
	}
	
	
	/**
	 * Gets the formatted item of the job
	 * 
	 * @return the formatted item
	 * @author Picono435
	 */
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
