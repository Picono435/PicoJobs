package com.gmail.picono435.picojobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.commands.JobsCommand;
import com.gmail.picono435.picojobs.hooks.PlaceholdersHook;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.listeners.ClickInventoryListener;
import com.gmail.picono435.picojobs.listeners.CreatePlayerListener;
import com.gmail.picono435.picojobs.listeners.jobs.MinerListener;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class PicoJobsPlugin extends JavaPlugin {

	//PLUGIN
	private static Plugin plugin;
	private static PicoJobsPlugin instance;
	private static boolean legacy;
	//DATA
	//JOBS DATA
	public static Map<String, Job> jobs = new HashMap<String, Job>(); 
	//PLAYERS DATA
	public static Map<UUID, JobPlayer> playersdata = new HashMap<UUID, JobPlayer>();
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
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Creating and configuring internal files...");
		LanguageManager.createLanguageFile();
		if(!FileCreator.generateFiles());
		
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Getting data from configuration files...");
		if(!generateJobsFromConfig()) return;
		if(!generatePlayers()) return;
		
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Setting up optional and required dependencies...");
		VaultHook.setupVault();
		PlaceholdersHook.setupPlaceholderAPI();
	
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Finishing enabling the plugin...");
		//REGISTERING COMMANDS
		this.getCommand("jobs").setExecutor(new JobsCommand());
		//REGISTERING LISTENERS
		Bukkit.getPluginManager().registerEvents(new CreatePlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new MinerListener(), this);
		
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugin was succefully enabled.");
		
		checkVersion();
	}
	
	public void onDisable() {
		sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Saving data and configurations...");
		jobs.clear();
		FileCreator.getDataFile().delete();
		if(!FileCreator.createDataFile()) return;
		ConfigurationSection playerDataCategory = FileCreator.getData().getConfigurationSection("playerdata");
		for(UUID uuid : playersdata.keySet()) {
			JobPlayer jp = playersdata.get(uuid);
			ConfigurationSection player = playerDataCategory.createSection(uuid.toString());
			if(jp.getJob() == null) {
				player.set("job", null);
			} else {
				player.set("job", jp.getJob().getName());
			}
			player.set("method", jp.getMethod());
			player.set("level", jp.getMethodLevel());
			player.set("salary", jp.getSalary());
			player.set("is-working", jp.isWorking());
		}
		
		try {
			FileCreator.getData().save(FileCreator.getDataFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sendConsoleMessage(ChatColor.GREEN + "[PicoJobs] The plugin was succefully disabled.");
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
	
	private static boolean generateJobsFromConfig() {
		ConfigurationSection jobsc = instance.getConfig().getConfigurationSection("jobs");
		for(String jobname : jobsc.getKeys(false)) {
			ConfigurationSection jobc = jobsc.getConfigurationSection(jobname);
			String displayname = jobc.getString("displayname");
			String tag = jobc.getString("tag");
			String type = jobc.getString("type");
			double method = getJobMethodFromConfig(jobname, type);
			double salary = jobc.getDouble("salary");
			ConfigurationSection guic = jobc.getConfigurationSection("gui");
			int slot = guic.getInt("slot");
			String item = guic.getString("item");
			int itemData = guic.getInt("item-data");
			boolean enchanted = guic.getBoolean("enchanted");
			
			Job job = new Job(jobname, displayname, tag, Type.getType(type.toUpperCase()), method, salary, slot, item, itemData, enchanted);
			jobs.put(jobname, job);
		}
		return true;
	}
	
	private static double getJobMethodFromConfig(String jobname, String type) {
		ConfigurationSection cat =  instance.getConfig().getConfigurationSection("jobs").getConfigurationSection(jobname);
		if(type.equals("miner")) {
			return cat.getDouble("blocks");
		}
		if(type.startsWith("kill")) {
			return cat.getDouble("kills");
		}
		if(type.equals("fisher")) {
			return cat.getDouble("fish");
		}
		return 0.0;
	}
	
	private static boolean generatePlayers() {
		FileConfiguration data = FileCreator.getData();
		if(data.getConfigurationSection("playerdata") == null) return true;
		for(String uuid : data.getConfigurationSection("playerdata").getKeys(false)) {
			if(uuid.equals("none")) continue;
			ConfigurationSection playerCategory = data.getConfigurationSection("playerdata").getConfigurationSection(uuid);
			Job job = PicoJobsAPI.getJobsManager().getJob(playerCategory.getString("job"));
			double method = playerCategory.getDouble("method");
			double level = playerCategory.getDouble("level");
			double salary = playerCategory.getDouble("salary");
			boolean isWorking = playerCategory.getBoolean("is-working");
			JobPlayer jp = new JobPlayer(job, method, level, salary, isWorking);
			playersdata.put(UUID.fromString(uuid), jp);
		}
		return true;
	}
	
	private boolean verificarLicenca() {
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] You are using the FREE version of the plugin!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Want to buy the premium version? Buy it in our site.");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] Our site is: https://piconodev.tk/plugins/premium");
		return true;
	}
	
	private static boolean checkLegacy() {
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
            URL url = new URL("https://api.github.com/repos/Picono435/PicoJobs/releases/latest");
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
            JSONObject json = (JSONObject) parser.parse(content.toString());
            
            version = (String)json.get("tag_name");
			
		} catch(Exception ex) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoJobs] Could not get the lastest version.");
			return;
		}
		try {
			DefaultArtifactVersion pluginVesion = new DefaultArtifactVersion(getPlugin().getDescription().getVersion());
			DefaultArtifactVersion lastestVersion = new DefaultArtifactVersion(version);
			if(lastestVersion.compareTo(pluginVesion) > 0) {
				new BukkitRunnable() {
					public void run() {
						sendConsoleMessage(ChatColor.DARK_RED + "[PicoJobs] You are using a old version of the plugin. Please download the new version in our pages.");
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
