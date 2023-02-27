package com.gmail.picono435.picojobs.api;

import java.util.*;
import java.util.stream.Collectors;

import com.gmail.picono435.picojobs.utils.ColorConverter;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;
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
	private List<Type> types;
	private double method;
	private double salary;
	private double maxSalary;
	private boolean requirePermission;
	private double salaryFrequency;
	private double methodFrequency;
	private String economy;
	private String workMessage;
	
	// GUI SETTINGS
	private int slot;
	private Material item;
	private int itemData;
	private boolean enchanted;
	private List<String> lore;
	
	// OPTIONAL
	private boolean useWhitelist;
	private Map<Type, List<Object>> whitelist;
	private Map<Type, List<String>> stringWhitelist = new HashMap<>();

	public Job(JsonObject jsonObject) {
		this(
				jsonObject.get("id").getAsString(),
				jsonObject.get("displayname").getAsString(),
				jsonObject.get("tag").getAsString(),
				jsonObject.get("types").getAsJsonArray().asList().stream().map(JsonElement::getAsString).map(Type::getType).collect(Collectors.toList()),
				jsonObject.get("method").getAsDouble(),
				jsonObject.get("salary").getAsDouble(),
				jsonObject.get("maxSalary").getAsDouble(),
				jsonObject.get("requirePermission").getAsBoolean(),
				jsonObject.get("salaryFrequency").getAsDouble(),
				jsonObject.get("methodFrequency").getAsDouble(),
				jsonObject.get("economy").getAsString(),
				jsonObject.get("workMessage") instanceof JsonNull ? null : jsonObject.get("workMessage").getAsString(),
				jsonObject.get("gui").getAsJsonObject().get("slot").getAsInt(),
				jsonObject.get("gui").getAsJsonObject().get("item").getAsString(),
				jsonObject.get("gui").getAsJsonObject().get("itemData").getAsInt(),
				jsonObject.get("gui").getAsJsonObject().get("enchanted").getAsBoolean(),
				jsonObject.get("gui").getAsJsonObject().get("lore").getAsJsonArray().asList().stream().map(JsonElement::getAsString).collect(Collectors.toList()),
				jsonObject.get("useWhitelist").getAsBoolean(),
				(new Gson()).fromJson(jsonObject.get("whitelist").toString(), new TypeToken<Map<Type, List<String>>>(){}.getType())
		);
	}

	public Job(String id, String displayname, String tag, List<Type> types, double method, double salary, double maxSalary, boolean requirePermission, double salaryFrequency, double methodFrequency, String economy, String workMessage, int slot, String item, int itemData, boolean enchanted, List<String> lore, boolean useWhitelist, Map<Type, List<String>> whitelist) {
		this.id = id;
		this.displayname = displayname;
		this.tag = tag;
		this.types = types;
		this.method = method;
		this.salary = salary;
		this.maxSalary = maxSalary;
		this.requirePermission = requirePermission;
		this.salaryFrequency = salaryFrequency;
		this.methodFrequency = methodFrequency;
		this.economy = economy;
		this.workMessage = workMessage;

		this.slot = slot;
		Material m = OtherUtils.matchMaterial(item);
		this.item = m;
		this.itemData = itemData;
		this.enchanted = enchanted;
		this.lore = lore;

		this.useWhitelist = useWhitelist;
		if(whitelist != null) {
			this.whitelist = new HashMap<>();
			for(Type type : whitelist.keySet()) {
				String whitelistType = type.getWhitelistType();
				List<Object> objects = new ArrayList<>();
				List<String> filteredNames = new ArrayList<>();
				if(whitelistType.equals("material")) {
					for(String s : whitelist.get(type)) {
						Material matNew = OtherUtils.matchMaterial(s);
						if(matNew == null) continue;
						filteredNames.add("minecraft:" + matNew.toString().toLowerCase());
						objects.add(matNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				} else if(whitelistType.equals("entity")) {
					for(String s : whitelist.get(type)) {
						EntityType entityNew = OtherUtils.getEntityByName(s);
						if(entityNew == null) continue;
						filteredNames.add("minecraft:" + entityNew.toString().toLowerCase());
						objects.add(entityNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				} else if(whitelistType.equals("job")) {
					Job j = this;
					new BukkitRunnable() {
						public void run() {
							for(String s : whitelist.get(type)) {
								Job jobNew = PicoJobsAPI.getJobsManager().getJob(s);
								if(jobNew == null) continue;
								filteredNames.add(jobNew.getID());
								objects.add(jobNew);
							}
							j.whitelist.put(type, objects);
							j.stringWhitelist.put(type, filteredNames);
						}
					}.runTask(PicoJobsPlugin.getInstance());
				} else if(whitelistType.equals("color")) {
					for(String s : whitelist.get(type)) {
						DyeColor colorNew = DyeColor.valueOf(s.toUpperCase(Locale.ROOT));
						if(colorNew == null) continue;
						filteredNames.add(colorNew.toString());
						objects.add(colorNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				}
			}
		} else {
			this.whitelist = new HashMap<>();
		}
	}
	
	/**
	 * Gets the id of the job
	 * 
	 * @return the name of the job
	 * @author Picono435
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Gets the display name of the job
	 * 
	 * @return the display name of the job
	 * @author Picono435
	 */
	public String getDisplayName() {
		return ColorConverter.translateAlternateColorCodes(this.displayname);
	}
	
	/**
	 * Gets the tag of the job
	 * 
	 * @return the tag of the job
	 * @author Picono435
	 */
	public String getTag() {
		return ColorConverter.translateAlternateColorCodes(this.tag);
	}
	
	/**
	 * Gets the type of the job
	 * 
	 * @return the type of the job
	 * @author Picono435
	 */
	public List<Type> getTypes() {
		if(this.types == null) this.types = new ArrayList<>();
		return this.types;
	}
	
	/**
	 * Gets the method of the job
	 * 
	 * @return the method of the job
	 * @author Picono435
	 */
	public double getMethod() {
		return this.method;
	}
	
	/**
	 * Gets the salary of the job
	 * 
	 * @return the salary of the job
	 * @author Picono435
	 */
	public double getSalary() {
		return this.salary;
	}
	

	/**
	 * Gets the max salary of the job
	 * 
	 * @return the max salary of the job
	 * @author Picono435
	 */
	public double getMaxSalary() {
		return this.maxSalary;
	}
	
	/**
	 * Check if the job requires permission to enter or not
	 * 
	 * @return true if it requires permission, false if not
	 * @author Picono435
	 */
	public boolean requirePermission() {
		return this.requirePermission;
	}
	
	/**
	 * Gets the salary frequency of the job
	 * 
	 * @return the salary frequency of the job
	 * @author Picono435
	 */
	public double getSalaryFrequency() {
		/*if(this.salaryFrequency == 0) {
			this.salaryFrequency = 0.3D;
		}*/
		return this.salaryFrequency;
	}
	
	/**
	 * Gets the method frequency of the job
	 * 
	 * @return the method frequency of the job
	 * @author Picono435
	 */
	public double getMethodFrequency() {
		/*if(this.methodFrequency == 0) {
			this.methodFrequency = 0.3D;
		}*/
		return this.methodFrequency;
	}
	
	/**
	 * Gets the economy name of the job
	 * 
	 * @return the economy implementation name
	 * @author Picono435
	 */
	public String getEconomy() {
		if(this.economy == null) {
			return "VAULT";
		}
		return this.economy;
	}
	
	/**
	 * Gets the work message of the job
	 * 
	 * @return the work message, empty if not found
	 * @author Picono435
	 */
	public String getWorkMessage() {
		String configString = types.get(0).name().toLowerCase() + "-work";
		String work = "";
		
		if(this.workMessage == null) {
			work = LanguageManager.getFormat(configString, null);
		} else {
			work = PlaceholderAPIHook.setPlaceholders(null, ColorConverter.translateAlternateColorCodes(workMessage));
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
		return this.slot - 1;
	}
	
	/**
	 * Gets the item material
	 * 
	 * @return the material of the job item
	 * @author Picono435
	 */
	public Material getMaterial() {
		if(this.item == null) this.item = Material.STONE;
		return this.item;
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
		return this.itemData;
	}
	
	/**
	 * Checks if the job item is enchanted or not
	 * 
	 * @return true if the job item is enchanted, false if not
	 * @author Picono435
	 */
	public boolean isEnchanted() {
		return this.enchanted;
	}

	/**
	 * Gets the item lore
	 *
	 * @return the item lore of the job item
	 * @author Picono435
	 */
	public List<String> getLore() {
		return this.lore;
	}
	
	/**
	 * Checks if it's whitelist or not
	 * 
	 * @return true if it's whitelist, false if it is blacklist
	 * @author Picono435
	 */
	public boolean isWhitelist() {
		return this.useWhitelist;
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
		if(PicoJobsPlugin.getInstance().isLessThan("1.12.2")) {
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
		builder.setLore(getLore());
		builder.removeAttributes();
		return builder.toItemStack();
	}
	
	/**
	 * Checks if a material is in the whitelist
	 *
	 * @param type the job type to get the whitelist from
	 * @param material the material that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Type type, Material material) {
		if(this.whitelist == null) return (this.useWhitelist) ? false : true;
		if(this.whitelist.size() <= 0) return (this.useWhitelist) ? false : true;
		if(this.useWhitelist) {
			return this.whitelist.get(type).contains(material);
		} else {
			return !this.whitelist.get(type).contains(material);
		}
	}
	
	/**
	 * Checks if a entity is in the whitelist
	 *
	 * @param type the job type to get the whitelist from
	 * @param entity the entity that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Type type, EntityType entity) {
		if(this.whitelist == null) return (this.useWhitelist) ? false : true;
		if(this.whitelist.size() <= 0) return (this.useWhitelist) ? false : true;
		if(this.useWhitelist) {
			return this.whitelist.get(type).contains(entity);
		} else {
			return !this.whitelist.get(type).contains(entity);
		}
	}
	
	/**
	 * Checks if a dye color is in the whitelist
	 *
	 * @param type the job type to get the whitelist from
	 * @param dyecolor the dye color that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Type type, DyeColor dyecolor) {
		if(this.whitelist == null) return (this.useWhitelist) ? false : true;
		if(this.whitelist.size() <= 0) return (this.useWhitelist) ? false : true;
		if(this.useWhitelist) {
			return this.whitelist.get(type).contains(dyecolor);
		} else {
			return !this.whitelist.get(type).contains(dyecolor);
		}
	}
	
	/**
	 * Checks if a job is in the whitelist
	 *
	 * @param type the job type to get the whitelist from
	 * @param job the job that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Type type, Job job) {
		if(this.whitelist == null) return (this.useWhitelist) ? false : true;
		if(this.whitelist.size() <= 0) return (this.useWhitelist) ? false : true;
		if(job == null) return (this.useWhitelist) ? false : true;
		if(this.useWhitelist) {
			return this.whitelist.get(type).contains(job);
		} else {
			return !this.whitelist.get(type).contains(job);
		}
	}
	
	/**
	 * Gets the whitelist
	 * 
	 * @return the whitelist
	 * @author Picono435
	 */
	public Map<Type, List<Object>> getWhitelist() {
		return whitelist;
	}

	/**
	 * Gets the string whitelist
	 * 
	 * @return the string whitelist
	 * @author Picono435
	 */
	public Map<Type, List<String>> getStringWhitelist() {
		return stringWhitelist;
	}

	/**
	 * Gets a formatted whitelist string
	 * 
	 * @return a formatted string with the array
	 * @author Picono435
	 */
	public String getWhitelistArray() {
		return Arrays.toString(this.stringWhitelist.values().toArray());
	}

	public JsonObject toJsonObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", this.id);
		jsonObject.addProperty("displayname", displayname);
		jsonObject.addProperty("tag", tag);

		JsonArray jsonTypes = new JsonArray();
		for(Type type : this.types) jsonTypes.add(type.name());
		jsonObject.add("types", jsonTypes);

		jsonObject.addProperty("method", this.method);
		jsonObject.addProperty("salary", this.salary);
		jsonObject.addProperty("maxSalary", this.maxSalary);
		jsonObject.addProperty("requirePermission", this.requirePermission);
		jsonObject.addProperty("salaryFrequency", this.salaryFrequency);
		jsonObject.addProperty("methodFrequency", this.methodFrequency);
		jsonObject.addProperty("economy", this.economy);
		jsonObject.addProperty("workMessage", this.workMessage);
		jsonObject.addProperty("useWhitelist", this.useWhitelist);

		JsonObject jsonWhitelist = new JsonObject();
		for(Type type : stringWhitelist.keySet()) {
			JsonArray jsonWhitelistArray = new JsonArray();
			for(String string : stringWhitelist.get(type)) {
				jsonWhitelistArray.add(string);
			}
			jsonWhitelist.add(type.name(), jsonWhitelistArray);
		}
		jsonObject.add("whitelist", jsonWhitelist);

		JsonObject jsonGui = new JsonObject();
		jsonGui.addProperty("slot", this.slot);
		jsonGui.addProperty("item", "minecraft:" + this.item.name().toLowerCase());
		jsonGui.addProperty("itemData", this.itemData);
		jsonGui.addProperty("enchanted", this.enchanted);
		JsonArray loreArray = new JsonArray();
		lore.forEach(loreArray::add);
		jsonGui.add("lore", loreArray);
		jsonObject.add("gui", jsonGui);

		return jsonObject;
	}

	public ConfigurationSection toYamlConfiguration() {
		ConfigurationSection jobConfiguration = new YamlConfiguration();
		jobConfiguration.set("id", this.id);
		jobConfiguration.set("displayname", displayname);
		jobConfiguration.set("tag", tag);

		List<String> listTypes = new ArrayList<>();
		for(Type type : this.types) listTypes.add(type.name());
		jobConfiguration.set("types", listTypes);

		jobConfiguration.set("method", this.method);
		jobConfiguration.set("salary", this.salary);
		jobConfiguration.set("max-salary", this.maxSalary);
		jobConfiguration.set("require-permission", this.requirePermission);
		jobConfiguration.set("salary-frequency", this.salaryFrequency);
		jobConfiguration.set("method-frequency", this.methodFrequency);
		jobConfiguration.set("economy", this.economy);
		jobConfiguration.set("work-message", this.workMessage);
		jobConfiguration.set("use-whitelist", this.useWhitelist);

		for(Type type : stringWhitelist.keySet()) {
			jobConfiguration.set("whitelist." + type.name(), stringWhitelist.get(type));
		}

		Map<String, Object> configGui = new HashMap<>();
		configGui.put("slot", this.slot);
		configGui.put("item", "minecraft:" + this.item.name().toLowerCase());
		configGui.put("itemData", this.itemData);
		configGui.put("enchanted", this.enchanted);
		configGui.put("lore", lore);
		jobConfiguration.createSection("gui", configGui);
		return jobConfiguration;
	}
}
