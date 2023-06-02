package com.gmail.picono435.picojobs;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;

import com.gmail.picono435.picojobs.api.*;
import com.gmail.picono435.picojobs.hooks.economy.CommandImplementation;
import com.gmail.picono435.picojobs.hooks.economy.ItemImplementation;
import com.gmail.picono435.picojobs.hooks.workzones.BiomeImplementation;
import com.gmail.picono435.picojobs.hooks.workzones.GriefPreventionImplementation;
import com.gmail.picono435.picojobs.hooks.workzones.WorldGuardImplementation;
import com.gmail.picono435.picojobs.hooks.workzones.WorldImplementation;
import com.gmail.picono435.picojobs.listeners.jobs.*;
import com.gmail.picono435.picojobs.storage.sql.H2Storage;
import com.gmail.picono435.picojobs.utils.GitHubAPI;
import io.github.slimjar.resolver.data.Repository;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.commands.JobsAdminCommand;
import com.gmail.picono435.picojobs.commands.JobsCommand;
import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;
import com.gmail.picono435.picojobs.hooks.PlayerPointsHook;
import com.gmail.picono435.picojobs.hooks.VaultHook;
import com.gmail.picono435.picojobs.hooks.economy.ExpImplementation;
import com.gmail.picono435.picojobs.hooks.economy.TokenManagerImplementation;
import com.gmail.picono435.picojobs.listeners.AliasesListeners;
import com.gmail.picono435.picojobs.listeners.ClickInventoryListener;
import com.gmail.picono435.picojobs.listeners.CreatePlayerListener;
import com.gmail.picono435.picojobs.listeners.ExecuteCommandListener;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.slimjar.app.builder.ApplicationBuilder;
import org.h2.tools.RunScript;
import org.h2.tools.Script;

public class PicoJobsPlugin extends JavaPlugin {

	private static PicoJobsPlugin instance;
	public static String EDITOR_STRING = "https://piconodev.com/editor";
	
	//PLUGIN
	private boolean wasUpdated;
	private String serverVersion;
	private boolean oldVersion;
	private String lastestPluginVersion;
	private String downloadUrl;
	private Metrics metrics;
	//DATA
	public Map<String, EconomyImplementation> economies = new HashMap<>();
	public Map<String, WorkZoneImplementation> workZones = new HashMap<>();
	//JOBS DATA
	public Map<String, Job> jobs = new HashMap<String, Job>();

	@Override
	public void onLoad() {
		instance = this;
		try {
			this.getLogger().info("Loading dependencies, this might take some minutes when ran for the first time...");
			ApplicationBuilder
				.appending("PicoJobs")
				.mirrorSelector((collection, collection1) -> collection)
				.downloadDirectoryPath(getDataFolder().toPath().resolve("libraries"))
				.internalRepositories(Collections.singleton(new Repository(new URL("https://repo.maven.apache.org/maven2/"))))
				.build();
			this.getLogger().info("All dependencies were loaded sucessfully.");
		} catch (Exception e) {
			this.getLogger().severe("An error occuried while loading SLIMJAR, go into https://github.com/Picono435/PicoJobs/wiki/Common-Issues#dependency-loading-issues with the following error:");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onEnable() {
		this.getLogger().info("Plugin created by: Picono435#2011. Thank you for using it");
		
		if(checkLegacy() ) {
			this.getLogger().warning("Checked that you are using a LEGACY spigot/bukkit version. We will use the old Material Support.");
		}

		// CREATING AND CONFIGURING INTERNAL FILES
		saveDefaultConfig();

		LanguageManager.createLanguageFile();
		if(!FileCreator.generateFiles());
		if(!getConfig().contains("config-version") || !getConfig().getString("config-version").equalsIgnoreCase(getDescription().getVersion())) {
			this.getLogger().warning("You were using a old configuration file... Updating it and removing comments, for more information check our WIKI.");
			getConfig().options().copyDefaults(true);
			getConfig().set("config-version", getDescription().getVersion());
			saveConfig();
			FileCreator.migrateFiles();
			LanguageManager.updateFile();
		}
		
		if(!isTestEnvironment()) {
			// STARTING BSTATS
	        metrics = new Metrics(this, 8553);
		}
        
        // SETTING UP AND REQUIRED AND OPTIONAL DEPENDENCIES
        PicoJobsAPI.registerEconomy(new ExpImplementation());
		PicoJobsAPI.registerEconomy(new CommandImplementation());
		PicoJobsAPI.registerEconomy(new ItemImplementation());
        VaultHook.setupVault();
        PlayerPointsHook.setupPlayerPoints();
        PicoJobsAPI.registerEconomy(new TokenManagerImplementation());

		PicoJobsAPI.registerWorkZone(new BiomeImplementation());
		PicoJobsAPI.registerWorkZone(new WorldImplementation());
		PicoJobsAPI.registerWorkZone(new WorldGuardImplementation());
		PicoJobsAPI.registerWorkZone(new GriefPreventionImplementation());

        PlaceholderAPIHook.setupPlaceholderAPI();
        new BukkitRunnable() {
        	public void run() {
        		Bukkit.getConsoleSender().sendMessage("[PicoJobs] " + economies.size() + " " + ChatColor.GREEN + "economy implementations successfully registered!");
				Bukkit.getConsoleSender().sendMessage("[PicoJobs] " + workZones.size() + " " + ChatColor.GREEN + "work zones implementations successfully registered!");
        	}
        }.runTaskLater(this, 1L);
        
        // GENERATE JOBS FROM CONFIGURATION
		this.getLogger().info("Generating jobs from configuration...");
		if(!generateJobsFromConfig()) return;

		// Fix my dumb way to migrate the database! This will be removed asap.
		if(PicoJobsAPI.getSettingsManager().getStorageMethod().equalsIgnoreCase("H2")) {
			try {
				if(PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("script").toFile().exists()) {
					RunScript.main("-url jdbc:h2:$f -script $script"
							.replace("$f", PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString())
							.replace("$script", PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("script").toString())
							.split(" "));
					PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("script").toFile().deleteOnExit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		PicoJobsAPI.getStorageManager().initializeStorageFactory();
		if(!isTestEnvironment()) {
			metrics.addCustomChart(new SingleLineChart("created_jobs", new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return jobs.size();
				}
			}));
		}
			
		// REGISTERING COMMANDS
		this.getCommand("jobs").setExecutor(new JobsCommand());
		this.getCommand("jobsadmin").setExecutor(new JobsAdminCommand());
		// REGISTERING LISTENERS
		Bukkit.getPluginManager().registerEvents(new CreatePlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new ExecuteCommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new AliasesListeners(), this);
		Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new TameListener(), this);
		Bukkit.getPluginManager().registerEvents(new ShearListener(), this);
		Bukkit.getPluginManager().registerEvents(new FillListener(), this);
		Bukkit.getPluginManager().registerEvents(new KillerListener(), this);
		Bukkit.getPluginManager().registerEvents(new FisherListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
		Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
		Bukkit.getPluginManager().registerEvents(new EatListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
		Bukkit.getPluginManager().registerEvents(new MilkListener(), this);
		Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new TradeListener(), this);
		Bukkit.getPluginManager().registerEvents(new RepairListener(), this);
		Bukkit.getPluginManager().registerEvents(new SmeltListener(), this);
		Bukkit.getPluginManager().registerEvents(new KillEntityListener(), this);

		this.getLogger().info("The plugin was succefully enabled.");

		if(getConfig().getBoolean("update-checker")) {
			checkVersion();
		}
	}
	
	public void onDisable() {
		this.getLogger().info("Disconnecting connection to storage...");
		jobs.clear();

		PicoJobsAPI.getStorageManager().destroyStorageFactory();

		if(wasUpdated && PicoJobsAPI.getStorageManager().getStorageFactory() instanceof H2Storage) {
			try {
				Script.main("-url jdbc:h2:$f -script $f.zip -options compression zip".replace("$f", PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString()).split(" "));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		this.getLogger().info("The plugin was succefully disabled.");
	}
	
	public static PicoJobsPlugin getInstance() {
		return instance;
	}

	public boolean isTestEnvironment() {
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith("org.junit.")) {
				return true;
			}
		}
		return false;
	}

	public boolean isOldVersion() {
		return oldVersion;
	}
	
	public String getLastestPluginVersion() {
		return lastestPluginVersion;
	}
	
	public String getLastestDownloadUrl() {
		return downloadUrl;
	}
	
	public boolean generateJobsFromConfig() {
		jobs.clear();
		ConfigurationSection jobsc = FileCreator.getJobsConfig().getConfigurationSection("jobs");
		this.getLogger().info("Retrieving jobs from the config...");
		for(String jobid : jobsc.getKeys(false)) {
			this.getLogger().finest("Retrieving job " + jobid + " from the config.");
			ConfigurationSection jobc = jobsc.getConfigurationSection(jobid);
			String displayname = jobc.getString("displayname");
			this.getLogger().finest("Display name: " + displayname);
			String tag = jobc.getString("tag");
			this.getLogger().finest("Tag: " + tag);
			if(jobc.contains("type")) {
				String typeString = jobc.getString("type");
				jobc.set("types", Collections.singletonList(typeString));
				jobc.set("type", null);
				try {
					FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			List<Type> types = Type.getTypes(jobc.getStringList("types"));
			this.getLogger().finest("Types: " + Arrays.toString(types.toArray()));
			double method = jobc.getDouble("method");
			this.getLogger().finest("Method: " + method);
			double salary = jobc.getDouble("salary");
			this.getLogger().finest("Salary: " + salary);
			double maxSalary = jobc.getDouble("max-salary");
			this.getLogger().finest("MaxSalary: " + maxSalary);
			boolean requiresPermission = jobc.getBoolean("require-permission");
			this.getLogger().finest("RequiresPermission: " + requiresPermission);
			double salaryFrequency = jobc.getDouble("salary-frequency");
			this.getLogger().finest("SalaryFrequency: " + salaryFrequency);
			double methodFrequency = jobc.getDouble("method-frequency");
			this.getLogger().finest("MethodFrequency: " + methodFrequency);
			String economy = jobc.getString("economy");
			if(economy != null) {
				economy = economy.toUpperCase(Locale.ROOT);
			}
			String workZone = jobc.getString("work-zone");
			if(workZone != null) {
				workZone = workZone.toUpperCase(Locale.ROOT);
			}
			String workMessage = jobc.getString("work-message");
			ConfigurationSection guic = jobc.getConfigurationSection("gui");
			int slot = guic.getInt("slot");
			String item = guic.getString("item");
			int itemData = guic.getInt("item-data");
			boolean enchanted = guic.getBoolean("enchanted");
			List<String> lore = guic.getStringList("lore");
			
			// CALCULATING OPTIONALS
			
			boolean useWhitelist = jobc.getBoolean("use-whitelist");
			Map<Type, List<String>> whitelist = new HashMap<>();
			if(jobc.contains("whitelist")) {
				// Legacy: Will be removed in future update
				if(jobc.get("whitelist") instanceof List) {
					List<String> white = jobc.getStringList("whitelist");
					for(Type type : types) {
						whitelist.put(type, white);
						jobc.set("whitelist." + type.name(), white);
						try {
							FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					for(String type : jobc.getConfigurationSection("whitelist").getKeys(false)) {
						whitelist.put(Type.getType(type), jobc.getConfigurationSection("whitelist").getStringList(type));
					}
				}
			}

			Job job = new Job(jobid, displayname, tag, types, method, salary, maxSalary, requiresPermission, salaryFrequency, methodFrequency, economy, workZone, workMessage, slot, item, itemData, enchanted, lore, useWhitelist, whitelist);

			jobs.put(jobid, job);
			
			if(!isTestEnvironment()) {
				metrics.addCustomChart(new DrilldownPie("jobs", () -> {
			        Map<String, Map<String, Integer>> map = new HashMap<>();
			        Map<String, Integer> entry = new HashMap<>();
			        entry.put(jobid, 1);
			        for(Type type : types) {
						map.put(type.name(), entry);
					}
			        return map;
			    }));

				metrics.addCustomChart(new DrilldownPie("active_economy", () -> {
			        Map<String, Map<String, Integer>> map = new HashMap<>();
			        Map<String, Integer> entry = new HashMap<>();
			        String eco = job.getEconomy();
			        if(eco.equalsIgnoreCase("VAULT")) {
			        	if(VaultHook.isEnabled() && VaultHook.hasEconomyPlugin()) {
			        		entry.put(VaultHook.getEconomy().getName(), 1);
				        } else {
				        	entry.put("Others", 1);
				        }
			        	map.put("VAULT", entry);
			        } else {
			        	entry.put(eco, 1);
			        	map.put(eco, entry);
			        }
			        return map;
			    }));
			}
		}
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
				this.serverVersion = serverVersionString;
				return true;
			}
			this.serverVersion = serverVersionString;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Same as having serverVersion &gt;= specifiedVersion
	 * Example: 1.18.1 &gt;= 1.13.2
	 *
	 * @param version
	 * @return
	 */
	public boolean isMoreThan(String version) {
		DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion(version);
		DefaultArtifactVersion serverVersionArt = new DefaultArtifactVersion(serverVersion);
		if(serverVersionArt.compareTo(legacyVersion) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Same as having serverVersion &lt;= specifiedVersion
	 * Example: 1.18.1 &lt;= 1.13.2
	 *
	 * @param version
	 * @return
	 */
	public boolean isLessThan(String version) {
		DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion(version);
		DefaultArtifactVersion serverVersionArt = new DefaultArtifactVersion(serverVersion);
		if(serverVersionArt.compareTo(legacyVersion) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if minecraft server that the plugin is running is
	 * equal or inferior to 1.13.2
	 *
	 * @return true if it is a legacy server, false otherwise
	 */
	public boolean isLegacyServer() {
		DefaultArtifactVersion legacyVersion = new DefaultArtifactVersion("1.13.2");
		DefaultArtifactVersion serverVersionArt = new DefaultArtifactVersion(serverVersion);
		if(serverVersionArt.compareTo(legacyVersion) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private void checkVersion() {
		String version = "1.0";
		try {
            URL url = new URL("https://servermods.forgesvc.net/servermods/files?projectIds=385252");
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

            JsonParser parser = new JsonParser();
            JsonArray jsonArray =  (JsonArray) parser.parse(content.toString());
            JsonObject json = (JsonObject) jsonArray.get(jsonArray.size() - 1);
            version = json.get("name").getAsString();
            version = version.replaceFirst("PicoJobs ", "");

			DefaultArtifactVersion pluginVersion = new DefaultArtifactVersion(getDescription().getVersion());
			DefaultArtifactVersion lastestVersion = new DefaultArtifactVersion(version);
			lastestPluginVersion = version;
			downloadUrl = json.get("downloadUrl").getAsString();
			boolean isRunningInOld = lastestVersion.compareTo(pluginVersion) > 0;
			if(getDescription().getVersion().endsWith("-DEV")) {
				isRunningInOld = !GitHubAPI.isTagLatest(version);
			}
			if(isRunningInOld) {
				new BukkitRunnable() {
					public void run() {
						PicoJobsPlugin.getInstance().getLogger().warning("Version: " + lastestVersion.toString() + " is out! You are still running version: " + pluginVersion.toString());
						if(getConfig().getBoolean("auto-update")) {
							if(updatePlugin(Bukkit.getConsoleSender(), "[PicoJobs] Plugin was updated to version "+ lastestVersion.toString() + " sucefully. Please restart the server to finish the update.")) {
								PicoJobsPlugin.getInstance().getLogger().info("Updating the plugin to the latest version...");
							} else {
								PicoJobsPlugin.getInstance().getLogger().warning("An error occuried while updating the plugin.");
							}
						}
						return;
					}
				}.runTaskLater(this, 40L);
				oldVersion = true;
			} else {
				new BukkitRunnable() {
					public void run() {
						PicoJobsPlugin.getInstance().getLogger().info("You are using the latest version of the plugin.");
						return;
					}
				}.runTaskLater(this, 40L);
			}
		} catch (Exception e) {
			this.getLogger().warning("Could not get the latest version.");
			e.printStackTrace();
			return;
		}
	}
	
	public boolean updatePlugin(CommandSender p, String message) {
		try {
			wasUpdated = true;
			URL url = new URL(PicoJobsPlugin.getInstance().getLastestDownloadUrl());
			
			Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			getFileMethod.setAccessible(true);
			File oldFile = (File) getFileMethod.invoke(PicoJobsPlugin.getInstance());

			File fileOutput = new File(Bukkit.getUpdateFolderFile().getPath() + File.separatorChar + oldFile.getName());
			if(!fileOutput.exists()) {
				fileOutput.mkdirs();
			}
			
			downloadFile(url, fileOutput, p, message);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	private void downloadFile(URL url, File fileOutput, CommandSender p, String message) {
		new BukkitRunnable() {
			public void run() {
				try {
					if (fileOutput.exists()) {
						fileOutput.delete();
				    }
					fileOutput.createNewFile();
				    OutputStream out = new BufferedOutputStream(new FileOutputStream(fileOutput.getPath()));
				    URLConnection conn = url.openConnection();
				    String encoded = Base64.getEncoder().encodeToString(("username"+":"+"password").getBytes(StandardCharsets.UTF_8));  //Java 8
				    conn.setRequestProperty("Authorization", "Basic "+ encoded);
				    InputStream in = conn.getInputStream();
				    byte[] buffer = new byte[1024];

				    int numRead;
				    while ((numRead = in.read(buffer)) != -1) {
				        out.write(buffer, 0, numRead);
				    }
				    if (in != null) {
				        in.close();
				    }
				    if (out != null) {
				        out.close();
				    }
				    p.sendMessage(message);
				    oldVersion = false;
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}.runTaskAsynchronously(PicoJobsPlugin.getInstance());
	}
}
