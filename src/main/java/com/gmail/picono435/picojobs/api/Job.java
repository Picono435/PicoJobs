package com.gmail.picono435.picojobs.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.ItemBuilder;
import com.gmail.picono435.picojobs.utils.OtherUtils;

/**
 * Represents a job.
 * 
 * @author Picono435
 *
 */
public class Job {
	
	private String id;
	private String displayname;
	private String tag;
	private Type type;
	private double method;
	private double salary;
	private boolean requiresPermission;
	private double salaryFrequency;
	private double methodFrequency;
	private String economy;
	private String workMessage;
	
	// GUI SETTINGS
	private int slot;
	private Material item;
	private int itemData;
	private boolean enchanted;
	
	// OPTIONAL
	private boolean useWhitelist;
	private List<Object> whitelist;
	private List<String> stringWhitelist;
	
	public Job(String id, String displayname, String tag, Type type, double method, double salary, boolean requiresPermission, double salaryFrequency, double methodFrequency, String economy, String workMessage, int slot, String item, int itemData, boolean enchanted, boolean useWhitelist, List<String> whitelist) {
		this.id = id;
		this.displayname = displayname;
		this.tag = tag;
		this.type = type;
		this.method = method;
		this.salary = salary;
		this.requiresPermission = requiresPermission;
		this.salaryFrequency = salaryFrequency;
		this.methodFrequency = methodFrequency;
		this.economy = economy;
		this.workMessage = workMessage;
		
		this.slot = slot;
		Material m = Material.matchMaterial(item);
		this.item = m;
		this.itemData = itemData;
		this.enchanted = enchanted;
		
		this.useWhitelist = useWhitelist;
		if(whitelist != null) {
			WhitelistConf whitelistConf = PicoJobsAPI.getJobsManager().getConfigWhitelist(type);
			if(whitelistConf == WhitelistConf.MATERIAL) {
				List<Material> list = new ArrayList<Material>();
				for(String s : whitelist) {
					list.add(Material.matchMaterial(s));
				}
				this.whitelist = new ArrayList<Object>(list);
			} else if(whitelistConf == WhitelistConf.ENTITY) {
				List<EntityType> list = new ArrayList<EntityType>();
				for(String s : whitelist) {
					list.add(OtherUtils.getEntityByName(s));
				}
				this.whitelist = new ArrayList<Object>(list);
			} else if(whitelistConf == WhitelistConf.JOB) {
				Job j = this;
				new BukkitRunnable() {
					public void run() {
						List<Job> list = new ArrayList<Job>();
						for(String s : whitelist) {
							System.out.println(s + " " + PicoJobsAPI.getJobsManager().getJob(s).getID());
							list.add(PicoJobsAPI.getJobsManager().getJob(s));
						}
						j.whitelist = new ArrayList<Object>(list);
					}
				}.runTask(PicoJobsPlugin.getInstance());
			}
			this.stringWhitelist = whitelist;
		} else {
			this.whitelist = new ArrayList<Object>();
		}
	}
	
	/**
	 * Gets the id of the job
	 * 
	 * @return the name of the job
	 * @author Picono435
	 */
	public String getID() {
		return id;
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
	 * Gets the work message of the job
	 * 
	 * @return the work message, empty if not found
	 * @author Picono435
	 */
	public String getWorkMessage() {
		String configString = type.name().toLowerCase() + "-work";
		String work = "";
		
		if(workMessage == null) {
			work = LanguageManager.getFormat(configString, null);
		} else {
			work = PlaceholderAPIHook.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', workMessage));
		}
		
		return work;
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
	 * Checks if it's whitelist or not
	 * 
	 * @return true if it's whitelist, false if it is blacklist
	 * @author Picono435
	 */
	public boolean isWhitelist() {
		return useWhitelist;
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
	 * Checks if a material is in the whitelist
	 * 
	 * @param material the material that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Material material) {
		if(whitelist == null) return (useWhitelist) ? false : true;
		if(whitelist.size() <= 0) return (useWhitelist) ? false : true;
		if(useWhitelist) {
			return whitelist.contains(material);
		} else {
			return !whitelist.contains(material);
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
		if(whitelist == null) return (useWhitelist) ? false : true;
		if(whitelist.size() <= 0) return (useWhitelist) ? false : true;
		if(useWhitelist) {
			return whitelist.contains(entity);
		} else {
			return !whitelist.contains(entity);
		}
	}
	
	/**
	 * Checks if a job is in the whitelist
	 * 
	 * @param job the job that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Job job) {
		if(whitelist == null) return (useWhitelist) ? false : true;
		if(whitelist.size() <= 0) return (useWhitelist) ? false : true;
		if(job == null) return (useWhitelist) ? false : true;
		if(useWhitelist) {
			return whitelist.contains(job);
		} else {
			return !whitelist.contains(job);
		}
	}
	
	/**
	 * Gets the whitelist
	 * 
	 * @return the whitelist
	 * @author Picono435
	 */
	public List<Object> getWhitelist() {
		return whitelist;
	}
	
	/**
	 * Gets the string whitelist
	 * 
	 * @return the string whitelist
	 * @author Picono435
	 */
	public List<String> getStringWhitelist() {
		return stringWhitelist;
	}
	
	/**
	 * Gets a formatted whitelist string
	 * 
	 * @return a formatted string with the array
	 * @author Picono435
	 */
	public String getWhitelistArray() {
		return Arrays.toString(stringWhitelist.toArray());
	}
	
	/**
	 * Sets the displayname of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param displayname the new displayname of the job
	 */
	public void setDisplayName(String displayname) {
		this.displayname = displayname;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("displayname", displayname);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the salary of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param salary the new salary of the job
	 */
	public void setSalary(double salary) {
		this.salary = salary;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("salary", salary);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the type of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param type the new type of the job
	 */
	public void setType(Type type) {
		this.type = type;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("type", type.name());
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the economy of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param economy the new economy of the job
	 */
	public void setEconomy(String economy) {
		this.economy = economy;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("economy", economy);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the method of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param method the new method of the job
	 */
	public void setMethod(double method) {
		this.method = method;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set(PicoJobsAPI.getJobsManager().getConfigMethod(getType()), method);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the salary frequency of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param salaryFrequency the new salary frequency of the job
	 */
	public void setSalaryFrequency(double salaryFrequency) {
		this.salaryFrequency = salaryFrequency;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("salary-frequency", salaryFrequency);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the use whitelist of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param useWhitelist the new use whitelist value of the job
	 */
	public void setWhitelistType(boolean useWhitelist) {
		this.useWhitelist = useWhitelist;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("use-whitelist", useWhitelist);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the whitelist of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param whitelist the new whitelist of the job
	 */
	public void setWhitelist(List<String> whitelist) {
		WhitelistConf whitelistConf = PicoJobsAPI.getJobsManager().getConfigWhitelist(type);
		if(whitelistConf == WhitelistConf.MATERIAL) {
			List<Material> list = new ArrayList<Material>();
			for(String s : whitelist) {
				list.add(Material.matchMaterial(s));
			}
			this.whitelist = new ArrayList<Object>(list);
		} else if(whitelistConf == WhitelistConf.ENTITY) {
			List<EntityType> list = new ArrayList<EntityType>();
			for(String s : whitelist) {
				list.add(OtherUtils.getEntityByName(s));
			}
			this.whitelist = new ArrayList<Object>(list);
		} else if(whitelistConf == WhitelistConf.JOB) {
			List<Job> list = new ArrayList<Job>();
			for(String s : whitelist) {
				list.add(PicoJobsAPI.getJobsManager().getJob(s));
			}
			this.whitelist = new ArrayList<Object>(list);
		}
		this.stringWhitelist = whitelist;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set(PicoJobsAPI.getJobsManager().getConfigWhitelistString(getType()), stringWhitelist);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the requiresPermission of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param requiresPermission the new requiresPermission of the job
	 */
	public void setRequiresPermission(boolean requiresPermission) {
		this.requiresPermission = requiresPermission;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("requires-permission", requiresPermission);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the method frequency of the job
	 * 
	 * This will save jobs.yml config in order to change in the configuration.
	 * 
	 * @param methodFrequency the new method frequency of the job
	 */
	public void setMethodFrequency(double methodFrequency) {
		this.methodFrequency = methodFrequency;
		try {
			FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(getID()).set("method-frequency", methodFrequency);
			FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
