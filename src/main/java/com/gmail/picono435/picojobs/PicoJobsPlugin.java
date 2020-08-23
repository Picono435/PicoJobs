package com.gmail.picono435.picojobs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.commands.JobsAdminCommand;
import com.gmail.picono435.picojobs.commands.JobsCommand;
import com.gmail.picono435.picojobs.hooks.PlaceholdersHook;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.listeners.AliasesListeners;
import com.gmail.picono435.picojobs.listeners.ClickInventoryListener;
import com.gmail.picono435.picojobs.listeners.CreatePlayerListener;
import com.gmail.picono435.picojobs.listeners.ExecuteCommandListener;
import com.gmail.picono435.picojobs.listeners.jobs.FisherListener;
import com.gmail.picono435.picojobs.listeners.jobs.KillerListener;
import com.gmail.picono435.picojobs.listeners.jobs.MilkListener;
import com.gmail.picono435.picojobs.listeners.jobs.PlaceListener;
import com.gmail.picono435.picojobs.listeners.jobs.RepairListener;
import com.gmail.picono435.picojobs.listeners.jobs.SmeltListener;
import com.gmail.picono435.picojobs.listeners.jobs.BreakListener;
import com.gmail.picono435.picojobs.listeners.jobs.CraftListener;
import com.gmail.picono435.picojobs.listeners.jobs.EatListener;
import com.gmail.picono435.picojobs.listeners.jobs.EnchantListener;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class PicoJobsPlugin extends JavaPlugin {

	//PLUGIN
	private static PicoJobsPlugin instance;
	private boolean legacy;
	private boolean oldVersion;
	private String lastestPluginVersion;
	//DATA
	public Map<String, EconomyImplementation> economies = new HashMap<String, EconomyImplementation>();
	//JOBS DATA
	public Map<String, Job> jobs = new HashMap<String, Job>(); 
	//PLAYERS DATA
	public Map<UUID, JobPlayer> playersdata = new HashMap<UUID, JobPlayer>();
	public Map<String, Integer> salary = new HashMap<String, Integer>();
	public Map<String, Boolean> inJob = new HashMap<String, Boolean>();
	
	public void onEnable() {
		instance = this;
		Bukkit.getServer();
		sendConsoleMessage("[PicoJobs] Plugin created by: Picono435#2011. Thank you for use it.");
		if(!verificarLicenca()) return;
		
		if(checkLegacy() ) {
			sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Checked that you are using a LEGACY spigot/bukkit version. We will use the old Material Support.");
		} else {
			sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] You are using a UPDATED spigot/bukkit. We will use the new Material Support.");
		}
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Creating and configuring internal files...");
		saveDefaultConfig();
		LanguageManager.createLanguageFile();
		if(!FileCreator.generateFiles());
		if(!getConfig().contains("config-version") || !getConfig().getString("config-version").equalsIgnoreCase(getDescription().getVersion())) {
			sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] You were using a old configuration file... Updating it and removing comments, for more information check our WIKI.");
			getConfig().options().copyDefaults(true);
			getConfig().set("config-version", getDescription().getVersion());
			saveConfig();
			LanguageManager.updateFile();
		}
		
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Getting data from storage...");
		if(!generateJobsFromConfig()) return;
		PicoJobsAPI.getStorageManager().getData();
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Setting up optional and required dependencies...");
		VaultHook.setupVault();
		PlaceholdersHook.setupPlaceholderAPI();
	
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Finishing enabling the plugin...");
		//REGISTERING COMMANDS
		this.getCommand("jobs").setExecutor(new JobsCommand());
		this.getCommand("jobsadmin").setExecutor(new JobsAdminCommand());
		//REGISTERING LISTENERS
		Bukkit.getPluginManager().registerEvents(new CreatePlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new ExecuteCommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new AliasesListeners(), this);
		Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new KillerListener(), this);
		Bukkit.getPluginManager().registerEvents(new FisherListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
		Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
		Bukkit.getPluginManager().registerEvents(new EatListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
		Bukkit.getPluginManager().registerEvents(new MilkListener(), this);
		Bukkit.getPluginManager().registerEvents(new RepairListener(), this);
		Bukkit.getPluginManager().registerEvents(new SmeltListener(), this);
		
		//STARTING BSTATS
        Metrics metrics = new Metrics(this, 8553);
        metrics.addCustomChart(new Metrics.SingleLineChart("created_jobs", new Callable<Integer>() {
        	@Override
        	public Integer call() throws Exception {
        		return jobs.size();
        	}
        }));
        metrics.addCustomChart(new Metrics.SimplePie("premium_version", () -> "Free"));
		
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugin was succefully enabled.");
		
		checkVersion();
				
		long saveInterval = PicoJobsAPI.getSettingsManager().getSaveInterval();
		if(saveInterval != 0) {
			new BukkitRunnable() {
				public void run() {
					PicoJobsAPI.getStorageManager().saveData(false);
				}
			}.runTaskTimerAsynchronously(this, saveInterval, saveInterval);
		}
	}
	
	public void onDisable() {
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Saving data and configurations...");
		jobs.clear();
		PicoJobsAPI.getStorageManager().saveData(true);
		
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugin was succefully disabled.");
	}
	
	public static PicoJobsPlugin getInstance() {
		return instance;
	}
	
	public void sendConsoleMessage(String message) {
		getServer().getConsoleSender().sendMessage(message);
	}
	
	public boolean isLegacy() {
		return legacy;
	}
	
	public boolean isOldVersion() {
		return oldVersion;
	}
	
	public String getLastestPluginVersion() {
		return lastestPluginVersion;
	}
	
	private boolean generateJobsFromConfig() {
		ConfigurationSection jobsc = FileCreator.getJobsConfig().getConfigurationSection("jobs");
		for(String jobname : jobsc.getKeys(false)) {
			ConfigurationSection jobc = jobsc.getConfigurationSection(jobname);
			String displayname = jobc.getString("displayname");
			String tag = jobc.getString("tag");
			String typeString = jobc.getString("type");
			Type type = Type.getType(typeString.toUpperCase());
			double method = getJobMethodFromConfig(jobname, type);
			double salary = jobc.getDouble("salary");
			boolean requiresPermission = jobc.getBoolean("require-permission");
			double salaryFrequency = jobc.getDouble("salary-frequency");
			double methodFrequency = jobc.getDouble("method-frequency");
			String economy = jobc.getString("economy");
			ConfigurationSection guic = jobc.getConfigurationSection("gui");
			int slot = guic.getInt("slot");
			String item = guic.getString("item");
			int itemData = guic.getInt("item-data");
			boolean enchanted = guic.getBoolean("enchanted");
			
			// CALCULATING OPTIONALS
			
			String killJob = "";
			if(type == Type.KILL && jobc.getString("kill-job") != null && !jobc.getString("kill-job").equalsIgnoreCase("all")) {
				killJob = jobc.getString("kill-job");
			}
			
			boolean useWhitelist = jobc.getBoolean("use-whitelist");
			List<String> blockWhitelist = null;
			if(type == Type.BREAK || type == Type.PLACE) {
				blockWhitelist = jobc.getStringList("block-whitelist");
			}
			if(type == Type.CRAFT || type == Type.SMELT || type == Type.ENCHANTING || type == Type.REPAIR || type == Type.EAT) {
				blockWhitelist = jobc.getStringList("item-whitelist");
			}
			
			Job job = new Job(jobname, displayname, tag, type, method, salary, requiresPermission, salaryFrequency, methodFrequency, economy, slot, item, itemData, enchanted, killJob, useWhitelist, blockWhitelist);
			jobs.put(jobname, job);
		}
		return true;
	}
	
	private double getJobMethodFromConfig(String jobname, Type type) {
		ConfigurationSection cat =  FileCreator.getJobsConfig().getConfigurationSection("jobs").getConfigurationSection(jobname);
		if(type == Type.BREAK || type == Type.PLACE) {
			return cat.getDouble("blocks");
		}
		if(type == Type.KILL) {
			return cat.getDouble("kills");
		}
		if(type == Type.FISHING) {
			return cat.getDouble("fish");
		}
		if(type == Type.CRAFT || type == Type.SMELT || type == Type.ENCHANTING || type == Type.REPAIR) {
			return cat.getDouble("items");
		}
		if(type == Type.EAT) {
			return cat.getDouble("food");
		}
		if(type == Type.MILK) {
			return cat.getDouble("buckets");
		}
		return 0.0;
	}
	
	private boolean verificarLicenca() {
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] You are using the FREE version of the plugin!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Want to buy the premium version? Buy it in our site.");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Our site is: https://piconodev.tk/plugins/premium");
		return true;
	}
	
	private boolean checkLegacy() {
		try {
			String serverVersionString = Bukkit.getBukkitVersion();
			int spaceIndex = serverVersionString.indexOf("-");
			serverVersionString = serverVersionString.substring(0, spaceIndex);
			DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion("1.13.2");
			DefaultArtifactVersion serverVersion = new DefaultArtifactVersion(serverVersionString);
			if(serverVersion.compareTo(legacyVersion) <= 0) {
				legacy = true;
			}
			return legacy;
		} catch (Exception e) {
			return legacy;
		}
	}
	
	private void checkVersion() {
		String version = "1.0";
		try {
            URL url = new URL("https://api.github.com/repos/Picono435/PicoJobs/releases");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(content.toString());
            JSONObject json = (JSONObject) jsonArray.get(0);
            version = (String)json.get("tag_name");
			
		} catch(Exception ex) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoJobs] Could not get the lastest version.");
			return;
		}
		try {
			DefaultArtifactVersion pluginVesion = new DefaultArtifactVersion(getDescription().getVersion());
			DefaultArtifactVersion lastestVersion = new DefaultArtifactVersion(version);
			lastestPluginVersion = version;
			if(lastestVersion.compareTo(pluginVesion) > 0) {
				new BukkitRunnable() {
					public void run() {
						sendConsoleMessage(ChatColor.DARK_RED + "[PicoJobs] You are using a old version of the plugin. Please download the new version in our pages.");
						oldVersion = true;
						return;
					}
				}.runTaskLater(this, 5L);
			} else {
				new BukkitRunnable() {
					public void run() {
						sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] You are using the lastest version of the plugin.");
						return;
					}
				}.runTaskLater(this, 5L);
			}
		} catch (Exception e) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoJobs] Could not get the lastest version.");
			return;
		}
	}
}
