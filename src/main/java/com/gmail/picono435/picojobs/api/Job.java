package com.gmail.picono435.picojobs.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.utils.ItemBuilder;
import com.gmail.picono435.picojobs.utils.OtherUtils;

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
	private double salaryFrequency;
	private double methodFrequency;
	private String economy;
	
	// GUI SETTINGS
	private int slot;
	private Material item;
	private int itemData;
	private boolean enchanted;
	
	// OPTIONAL
	private String killJob;
	private boolean useWhitelist;
	private List<Material> blockWhitelist;
	private List<EntityType> entityWhitelist;
	
	public Job(String name, String displayname, String tag, Type type, double method, double salary, boolean requiresPermission, double salaryFrequency, double methodFrequency, String economy, int slot, String item, int itemData, boolean enchanted, String killJob, boolean useWhitelist, List<String> blockWhitelist, List<String> entityWhitelist) {
		this.name = name;
		this.displayname = displayname;
		this.tag = tag;
		this.type = type;
		this.method = method;
		this.salary = salary;
		this.requiresPermission = requiresPermission;
		this.salaryFrequency = salaryFrequency;
		this.methodFrequency = methodFrequency;
		this.economy = economy;
		
		this.slot = slot;
		Material m = Material.matchMaterial(item);
		this.item = m;
		this.itemData = itemData;
		this.enchanted = enchanted;
		
		this.killJob = killJob;
		this.useWhitelist = useWhitelist;
		if(blockWhitelist != null) {
			List<Material> materialList = new ArrayList<Material>();
			for(String s : blockWhitelist) {
				materialList.add(Material.matchMaterial(s));
			}
			this.blockWhitelist = materialList;
		}
		if(entityWhitelist != null) {
			List<EntityType> entityList = new ArrayList<EntityType>();
			for(String s : entityWhitelist) {
				entityList.add(OtherUtils.getEntityByName(s));
			}
			this.entityWhitelist = entityList;
		}
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
		if(type == null) type = Type.BREAK;
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
	 * Gets the salary frequency of the job
	 * 
	 * @return the salary frequency of the job
	 * @author Picono435
	 */
	public double getSalaryFrequency() {
		if(salaryFrequency == 0) {
			salaryFrequency = 0.3D;
		}
		return salaryFrequency;
	}
	
	/**
	 * Gets the method frequency of the job
	 * 
	 * @return the method frequency of the job
	 * @author Picono435
	 */
	public double getMethodFrequency() {
		if(methodFrequency == 0) {
			methodFrequency = 0.3D;
		}
		return methodFrequency;
	}
	
	/**
	 * Gets the economy name of the job
	 * 
	 * @return the economy implementation name
	 * @author Picono435
	 */
	public String getEconomy() {
		if(economy == null) {
			return "VAULT";
		}
		return economy;
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
		if(item == null) item = Material.STONE;
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
		if(PicoJobsPlugin.getInstance().isLegacy()) {
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
	
	/**
	 * Gets the job that needs to be killed. This does not verify if its a KILL job type, it will return an empty string if there is no kill job.
	 * 
	 * @return the kill job, an empty string if there is none
	 * @author Picono435
	 */
	public String getKillJob() {
		return killJob;
	}
	
	/**
	 * Checks if a material is in the whitelist
	 * 
	 * @param material the material that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Material material) {
		if(blockWhitelist == null) return true;
		if(blockWhitelist.size() <= 0) return true;
		if(useWhitelist) {
			return blockWhitelist.contains(material);
		} else {
			return !blockWhitelist.contains(material);
		}
	}
	
	/**
	 * Checks if a entity is in the whitelist
	 * 
	 * @param entity the entity that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(EntityType entity) {
		if(entityWhitelist == null) return true;
		if(entityWhitelist.size() <= 0) return true;
		if(useWhitelist) {
			return entityWhitelist.contains(entity);
		} else {
			return !entityWhitelist.contains(entity);
		}
	}
}
