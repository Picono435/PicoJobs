package com.gmail.picono435.picojobs.api;

import java.util.*;
import java.util.stream.Collectors;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

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
	private String workZone;
	private String workMessage;
	
	// GUI SETTINGS
	private int slot;
	private String item;
	private int itemData;
	private boolean enchanted;
	private List<String> lore;
	
	// OPTIONAL
	private boolean useWhitelist;
	private Map<Type, List<String>> whitelist = new HashMap<>();

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
				jsonObject.get("workZone").getAsString(),
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

	public Job(String id, String displayname, String tag, List<Type> types, double method, double salary, double maxSalary, boolean requirePermission, double salaryFrequency, double methodFrequency, String economy, String workZone, String workMessage, int slot, String item, int itemData, boolean enchanted, List<String> lore, boolean useWhitelist, Map<Type, List<String>> whitelist) {
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
		this.workZone = workZone;
		this.workMessage = workMessage;

		this.slot = slot;
		this.item = item;
		this.itemData = itemData;
		this.enchanted = enchanted;
		this.lore = lore;

		this.useWhitelist = useWhitelist;
		this.whitelist = whitelist;
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
		return PicoJobsCommon.getColorConverter().translateAlternateColorCodes(this.displayname);
	}
	
	/**
	 * Gets the tag of the job
	 * 
	 * @return the tag of the job
	 * @author Picono435
	 */
	public String getTag() {
		return PicoJobsCommon.getColorConverter().translateAlternateColorCodes(this.tag);
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
	 * Gets the work zone name of the job
	 *
	 * @return the work zone implementation name
	 * @author Picono435
	 */
	public String getWorkZone() {
		if(this.workZone == null) return null;
		return this.workZone.toUpperCase(Locale.ROOT);
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
			work = LanguageManager.formatMessage("do %a% actions");
		} else {
			work = JobPlaceholders.setPlaceholders(null, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(workMessage));
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
	public String getMaterial() {
		if(this.item == null) this.item = "minecraft:stone";
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
	public ItemAdapter getFormattedItem() {
		ItemAdapter itemAdapter;
		if(PicoJobsCommon.isLessThan("1.13.2")) {
			int itemData = getItemData() - 1;
			if(itemData == -1) {
				itemAdapter = new ItemAdapter(getMaterial());
			} else {
				itemAdapter = new ItemAdapter(getMaterial(), 1, (byte)itemData);
			}
		} else {
			itemAdapter = new ItemAdapter(getMaterial());
		}
		itemAdapter.setName(getDisplayName());
		itemAdapter.setEnchanted(isEnchanted());
		itemAdapter.setLore(PicoJobsCommon.getColorConverter().translateAlternateColorCodes(getLore()));
		return itemAdapter;
	}
	
	/**
	 * Checks if a object is in the whitelist
	 *
	 * @param type the job type to get the whitelist from
	 * @param object the object that you want to check
	 * @return true if it's in the whitelist or there is no whitelist, false if not
	 * @author Picono435
	 */
	public boolean inWhitelist(Type type, Object object) {
		if(this.whitelist.size() <= 0) return !this.useWhitelist;
		if(!this.whitelist.containsKey(type)) return !this.useWhitelist;
		if(type.getWhitelistType() == Type.WhitelistType.JOB) {
			if(this.whitelist.get(type).contains(((Job) object).getID())) {
				return this.useWhitelist;
			} else {
				return !this.useWhitelist;
			}
		}
		if(PicoJobsCommon.getWhitelistConverter().inStringList(object, type, this.whitelist.get(type))) {
			return this.useWhitelist;
		} else {
			return !this.useWhitelist;
		}
	}

	public JsonObject toJsonObject() throws SerializationException {
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

		// TODO: Required Field of economy

		jsonObject.addProperty("workZone", this.workZone);

		// TODO: Required Field of workzone

		jsonObject.addProperty("workMessage", this.workMessage);
		jsonObject.addProperty("useWhitelist", this.useWhitelist);

		JsonObject jsonWhitelist = new JsonObject();
		for(Type type : whitelist.keySet()) {
			JsonArray jsonWhitelistArray = new JsonArray();
			for(String string : whitelist.get(type)) {
				jsonWhitelistArray.add(string);
			}
			jsonWhitelist.add(type.name(), jsonWhitelistArray);
		}
		jsonObject.add("whitelist", jsonWhitelist);

		JsonObject jsonGui = new JsonObject();
		jsonGui.addProperty("slot", this.slot);
		jsonGui.addProperty("item", this.item.toLowerCase());
		jsonGui.addProperty("itemData", this.itemData);
		jsonGui.addProperty("enchanted", this.enchanted);
		JsonArray loreArray = new JsonArray();
		lore.forEach(loreArray::add);
		jsonGui.add("lore", loreArray);
		jsonObject.add("gui", jsonGui);

		return jsonObject;
	}

	public ConfigurationNode toYamlConfiguration() throws ConfigurateException {
		ConfigurationNode jobConfiguration = YamlConfigurationLoader.builder().build().load();
		jobConfiguration.node("id").set(this.id);
		jobConfiguration.node("displayname").set(displayname);
		jobConfiguration.node("tag").set(tag);

		List<String> listTypes = new ArrayList<>();
		for(Type type : this.types) listTypes.add(type.name());
		jobConfiguration.node("types").set(listTypes);

		jobConfiguration.node("method").set(this.method);
		jobConfiguration.node("salary").set(this.salary);
		jobConfiguration.node("max-salary").set(this.maxSalary);
		jobConfiguration.node("require-permission").set(this.requirePermission);
		jobConfiguration.node("salary-frequency").set(this.salaryFrequency);
		jobConfiguration.node("method-frequency").set(this.methodFrequency);
		jobConfiguration.node("economy").set(this.economy);
		jobConfiguration.node("work-zone").set(this.workZone);
		jobConfiguration.node("work-message").set(this.workMessage);
		jobConfiguration.node("use-whitelist").set(this.useWhitelist);

		for(Type type : whitelist.keySet()) {
			jobConfiguration.node("whitelist", type.name()).set(whitelist.get(type));
		}

		jobConfiguration.node("gui", slot).set(this.slot);
		jobConfiguration.node("item").set(this.item);
		jobConfiguration.node("itemData").set(this.itemData);
		jobConfiguration.node("enchanted").set(this.enchanted);
		jobConfiguration.node("lore").set(lore);
		return jobConfiguration;
	}
}
