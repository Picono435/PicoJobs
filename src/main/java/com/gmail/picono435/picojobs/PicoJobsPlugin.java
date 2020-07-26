package com.gmail.picono435.picojobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.picono435.picojobs.commands.JobsCommand;
import com.gmail.picono435.picojobs.hooks.PlaceholdersHook;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.vars.Job;
import com.gmail.picono435.picojobs.vars.Type;

public class PicoJobsPlugin extends JavaPlugin {

	//PLUGIN
	private static Plugin plugin;
	private static PicoJobsPlugin instance;
	private static boolean legacy;
	//DATA
	//JOBS DATA
	public static Map<String, Job> jobs = new HashMap<String, Job>(); 
	//PLAYERS DATA
	public static Map<String, List<String>> playersdata = new HashMap<String, List<String>>();
	public static Map<String, Integer> salary = new HashMap<String, Integer>();
	public static Map<String, Boolean> inJob = new HashMap<String, Boolean>();
	
	public void onEnable() {
		plugin = this;
		instance = this;
		saveDefaultConfig();
		Bukkit.getServer();
		sendConsoleMessage("[PicoJobs] Plugin created by: Picono435#2011. Thank you for use it.");
		if(!verificarLicenca()) return;
		
		if(checkLegacy() ) {
			sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Checked that you are using a LEGACY spigot/bukkit version. We will use the old Material Support.");
		} else {
			sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] You are using a UPDATED spigot/bukkit. We will use the new Material Support.");
		}
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Creating and configurating the language file selected...");
		LanguageManager.createLanguageFile();
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Getting data from configuration files...");
		if(!generateJobsFromConfig()) return;
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Setting up optional and required dependencies...");
		VaultHook.setupVault();
		PlaceholdersHook.setupPlaceholderAPI();
	
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Finishing enabling the plugin...");
		this.getCommand("jobs").setExecutor(new JobsCommand());
		
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugin was succefully enabled.");
	}
	
	public void onDisable() {
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Saving data and configurations...");
		jobs.clear();
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugins was succefully disabled.");
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static PicoJobsPlugin getInstance() {
		return instance;
	}
	
	public static void sendConsoleMessage(String message) {
		getPlugin().getServer().getConsoleSender().sendMessage(message);
	}
	
	public static boolean isLegacy() {
		return legacy;
	}
	
	private static boolean checkLegacy() {
		DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion("1.13.2");
		DefaultArtifactVersion serverVersion = new DefaultArtifactVersion(Bukkit.getVersion());
		if(serverVersion.compareTo(legacyVersion) > 0) {
			legacy = true;
		}
		return legacy;
	}
	
	private boolean verificarLicenca() {
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] You are using the FREE version of the plugin!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Want to buy the premium version? Buy it in our site.");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Our site is: https://piconodev.tk/plugins/premium");
		return true;
	}
	
	private static boolean generateJobsFromConfig() {
		ConfigurationSection jobsc = instance.getConfig().getConfigurationSection("jobs");
		for(String jobname : jobsc.getKeys(false)) {
			ConfigurationSection jobc = jobsc.getConfigurationSection(jobname);
			String displayname = jobc.getString("displayname");
			String tag = jobc.getString("tag");
			String type = jobc.getString("type");
			int reqmethod = getJobRequiredMethodFromConfig(jobname, type);
			ConfigurationSection guic = jobc.getConfigurationSection("gui");
			int slot = guic.getInt("slot");
			String item = guic.getString("item");
			int itemData = guic.getInt("item-data");
			boolean enchanted = guic.getBoolean("enchanted");
			
			Job job = new Job(jobname, displayname, tag, Type.getType(type), reqmethod, slot, item, itemData, enchanted);
			jobs.put(jobname, job);
		}
		return true;
	}
	
	private static int getJobRequiredMethodFromConfig(String jobname, String type) {
		ConfigurationSection cat =  instance.getConfig().getConfigurationSection("jobs").getConfigurationSection(jobname);
		if(type.equals("miner")) {
			return cat.getInt("blocks");
		}
		if(type.startsWith("kill")) {
			return cat.getInt("kills");
		}
		if(type.equals("fisher")) {
			return cat.getInt("fish");
		}
		return 0;
	}
}
