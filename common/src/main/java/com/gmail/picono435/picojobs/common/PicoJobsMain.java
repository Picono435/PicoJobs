package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.api.*;
import com.gmail.picono435.picojobs.common.command.admin.JobsAdminCommand;
import com.gmail.picono435.picojobs.common.command.main.JobsCommand;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.common.utils.GitHubAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SingleLineChart;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class PicoJobsMain {

    public static String EDITOR_STRING = "https://piconodev.com/editor";
    //DATA
    public Map<String, EconomyImplementation> economies = new HashMap<>();
    public Map<String, WorkZoneImplementation> workZones = new HashMap<>();
    //JOBS DATA
    public Map<String, Job> jobs = new HashMap<String, Job>();
    private JobsCommand jobsCommand;
    private JobsAdminCommand jobsAdminCommand;

    public void init() {
        if(FileManager.getConfigNode().node("config-version").empty() || !FileManager.getConfigNode().node("config-version").getString().equalsIgnoreCase(PicoJobsCommon.getVersion())) {
            PicoJobsCommon.getLogger().warn("You were using a old configuration file... Updating it with the new configurations of the current version.");
            PicoJobsCommon.getFileManager().migrateFiles();
        }

        PicoJobsCommon.getSoftwareHooker().hookInPhase(SoftwareHooker.Phase.ONE);
        PicoJobsCommon.getSchedulerAdapter().executeSync(() -> {
            PicoJobsCommon.getLogger().info("[PicoJobs] " + economies.size() + " economy implementations successfully registered!");
            PicoJobsCommon.getLogger().info("[PicoJobs] " + workZones.size() + " work zones implementations successfully registered!");
        });

        PicoJobsCommon.getLogger().info("Generating jobs from the configuration files...");
        try {
            if(!generateJobsFromConfig()) return;
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }

        this.jobsCommand = new JobsCommand();
        this.jobsAdminCommand = new JobsAdminCommand();

        PicoJobsCommon.getSoftwareHooker().hookInPhase(SoftwareHooker.Phase.TWO);

        PicoJobsAPI.getStorageManager().initializeStorageFactory();

        // bStats Metrics
        if(PicoJobsCommon.getMetricsBase() != null) {
            PicoJobsCommon.getMetricsBase().addCustomChart(new SingleLineChart("created_jobs", () -> jobs.size()));
        }

        PicoJobsCommon.getSoftwareHooker().hookInPhase(SoftwareHooker.Phase.THREE);

        PicoJobsCommon.getLogger().info("The plugin was successfully loaded.");

        if(FileManager.getConfigNode().node("update-checker").getBoolean()) {
            checkVersion();
        }
    }

    public JobsCommand getJobsCommand() {
        return jobsCommand;
    }

    public JobsAdminCommand getJobsAdminCommand() {
        return jobsAdminCommand;
    }

    public boolean generateJobsFromConfig() throws SerializationException {
        jobs.clear();
        ConfigurationNode jobsNode = FileManager.getJobsNode().node("jobs");
        PicoJobsCommon.getLogger().info("Retrieving jobs from the config...");
        for(Object jobidObject : jobsNode.childrenMap().keySet()) {
            String jobid = (String) jobidObject;
            PicoJobsCommon.getLogger().debug("Retrieving job " + jobid + " from the config.");
            ConfigurationNode jobNode = jobsNode.node(jobid);
            String displayname = jobNode.node("displayname").getString();
            PicoJobsCommon.getLogger().debug("Display name: " + displayname);
            String tag = jobNode.node("tag").getString();
            PicoJobsCommon.getLogger().debug("Tag: " + tag);
            List<Type> types = Type.getTypes(jobNode.node("types").getList(String.class));
            PicoJobsCommon.getLogger().debug("Types: " + Arrays.toString(types.toArray()));
            double method = jobNode.node("method").getDouble();
            PicoJobsCommon.getLogger().debug("Method: " + method);
            double salary = jobNode.node("salary").getDouble();
            PicoJobsCommon.getLogger().debug("Salary: " + salary);
            double maxSalary = jobNode.node("max-salary").getDouble();
            PicoJobsCommon.getLogger().debug("MaxSalary: " + maxSalary);
            boolean requiresPermission = jobNode.node("require-permission").getBoolean();
            PicoJobsCommon.getLogger().debug("RequiresPermission: " + requiresPermission);
            double salaryFrequency = jobNode.node("salary-frequency").getDouble();
            PicoJobsCommon.getLogger().debug("SalaryFrequency: " + salaryFrequency);
            double methodFrequency = jobNode.node("method-frequency").getDouble();
            PicoJobsCommon.getLogger().debug("MethodFrequency: " + methodFrequency);
            String economy = jobNode.node("economy").getString();
            if(economy != null) {
                economy = economy.toUpperCase(Locale.ROOT);
            }
            PicoJobsCommon.getLogger().debug("Economy: " + economy);
            String workZone = jobNode.node("work-zone").getString();
            if(workZone != null) {
                workZone = workZone.toUpperCase(Locale.ROOT);
            }
            PicoJobsCommon.getLogger().debug("Work Zone: " + workZone);
            String workMessage = jobNode.node("work-message").getString();
            ConfigurationNode guiNode = jobNode.node("gui");
            int slot = guiNode.node("slot").getInt();
            String item = guiNode.node("item").getString();
            int itemData = guiNode.node("item-data").getInt();
            boolean enchanted = guiNode.node("enchanted").getBoolean();
            List<String> lore = guiNode.node("lore").getList(String.class);

            boolean useWhitelist = jobNode.node("use-whitelist").getBoolean();
            Map<Type, List<String>> whitelist = new HashMap<>();
            if(!jobNode.node("whitelist").empty()) {
                for(Object type : jobNode.node("whitelist").childrenMap().keySet()) {
                    whitelist.put(Type.getType((String)type), jobNode.node("whitelist", type).getList(String.class));
                }
            }

            Job job = new Job(jobid, displayname, tag, types, method, salary, maxSalary, requiresPermission, salaryFrequency, methodFrequency, economy, workZone, workMessage, slot, item, itemData, enchanted, lore, useWhitelist, whitelist);

            jobs.put(jobid, job);

            if(PicoJobsCommon.getMetricsBase() != null) {
                PicoJobsCommon.getMetricsBase().addCustomChart(new DrilldownPie("jobs", () -> {
                    Map<String, Map<String, Integer>> map = new HashMap<>();
                    Map<String, Integer> entry = new HashMap<>();
                    entry.put(jobid, 1);
                    for(Type type : types) {
                        map.put(type.name(), entry);
                    }
                    return map;
                }));

                PicoJobsCommon.getMetricsBase().addCustomChart(new DrilldownPie("active_economy", () -> {
                    Map<String, Map<String, Integer>> map = new HashMap<>();
                    Map<String, Integer> entry = new HashMap<>();
                    String eco = job.getEconomy();
                    entry.put(eco, 1);
                    map.put(eco, entry);
                    return map;
                }));

                PicoJobsCommon.getMetricsBase().addCustomChart(new DrilldownPie("active_workzone", () -> {
                    Map<String, Map<String, Integer>> map = new HashMap<>();
                    Map<String, Integer> entry = new HashMap<>();
                    String workz = job.getWorkZone();
                    entry.put(workz, 1);
                    map.put(workz, entry);
                    return map;
                }));
            }
    }
        return true;
}

    private void checkVersion() {
        try {
            URL url = new URL(String.format("https://api.modrinth.com/v2/project/picojobs/version?loaders=%s",
                    URLEncoder.encode("[\"" + PicoJobsCommon.getPlatform().name().toLowerCase() + "\"]", "UTF-8")));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("User-Agent", "Picono435/PicoJobs/" + PicoJobsCommon.getVersion() + " (piconodev.com)");

            int statusCode = connection.getResponseCode();
            if(statusCode == 401) {
                PicoJobsCommon.getLogger().warn("Could not access Modrinth API as the version in use has been permanently deprecated. Upgrade the plugin manually for an alternative.");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            if(jsonArray.size() <= 0) {
                PicoJobsCommon.getLogger().info("You are using the latest version of the plugin.");
                return;
            }
            JsonObject json = jsonArray.get(0).getAsJsonObject();
            String version = json.get("version_number").getAsString();

            boolean isRunningOld;
            if(PicoJobsCommon.getVersion().endsWith("-DEV")) {
                isRunningOld = !GitHubAPI.isTagLatest(version);
            } else {
                DefaultArtifactVersion pluginVersion = new DefaultArtifactVersion(PicoJobsCommon.getVersion());
                DefaultArtifactVersion latestVersion = new DefaultArtifactVersion(version);
                System.out.println("Checking versions: " + pluginVersion.toString() + " " + latestVersion.toString());
                isRunningOld = latestVersion.compareTo(pluginVersion) > 0;
            }
            if(isRunningOld) {
                PicoJobsCommon.getLogger().warn("Version: " + version + " is out! You are still running version: " + PicoJobsCommon.getVersion());
                if(FileManager.getConfigNode().node("auto-update").getBoolean() && PicoJobsCommon.getPlatform() == Platform.BUKKIT) {
                    String downloadUrl = json.getAsJsonArray("files").get(0).getAsJsonObject().get("url").getAsString();
                    updatePlugin(downloadUrl, version);
                }
            } else {
                PicoJobsCommon.getLogger().info("You are using the latest version of the plugin.");
            }
        } catch (Exception ex) {
            PicoJobsCommon.getLogger().warn("Could not get the latest version.");
            ex.printStackTrace();
        }
    }

    public void updatePlugin(String downloadUrl, String newVersion) {

    }
}
