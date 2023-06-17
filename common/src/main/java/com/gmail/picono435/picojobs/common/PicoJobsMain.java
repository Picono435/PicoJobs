package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.api.*;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.common.utils.GitHubAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PicoJobsMain {

    public static String EDITOR_STRING = "https://piconodev.com/editor";
    //DATA
    public Map<String, EconomyImplementation> economies = new HashMap<>();
    public Map<String, WorkZoneImplementation> workZones = new HashMap<>();
    //JOBS DATA
    public Map<String, Job> jobs = new HashMap<String, Job>();

    public void init() {
        PicoJobsCommon.getLogger().info("Plugin created by: Picono435#2011. Thank you for using it");

        if(!FileManager.getConfigNode().node("config-version").empty() || !FileManager.getConfigNode().node("config-version").getString().equalsIgnoreCase(PicoJobsCommon.getVersion())) {
            PicoJobsCommon.getLogger().warning("You were using a old configuration file... Updating it with the new configurations of the current version.");
            PicoJobsCommon.getFileManager().migrateFiles();
        }

        PicoJobsCommon.getSoftwareHooker().hookInPhase(SoftwareHooker.Phase.ONE);

        PicoJobsCommon.getLogger().info("Generating jobs from the configuration files...");
        try {
            if(!generateJobsFromConfig()) return;
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }

        PicoJobsAPI.getStorageManager().initializeStorageFactory();

        PicoJobsCommon.getLogger().info("The plugin was successfully loaded.");

        if(FileManager.getConfigNode().node("update-checker").getBoolean()) {
            checkVersion();
        }
    }

    public boolean generateJobsFromConfig() throws SerializationException {
        jobs.clear();
        ConfigurationNode jobsNode = FileManager.getJobsNode().node("jobs");
        PicoJobsCommon.getLogger().info("Retrieving jobs from the config...");
        for(Object jobidObject : jobsNode.childrenMap().keySet()) {
            String jobid = (String) jobidObject;
            PicoJobsCommon.getLogger().finest("Retrieving job " + jobid + " from the config.");
            ConfigurationNode jobNode = jobsNode.node(jobid);
            String displayname = jobNode.node("displayname").getString();
            PicoJobsCommon.getLogger().finest("Display name: " + displayname);
            String tag = jobNode.node("tag").getString();
            PicoJobsCommon.getLogger().finest("Tag: " + tag);
            List<Type> types = Type.getTypes(jobNode.node("types").getList(String.class));
            PicoJobsCommon.getLogger().finest("Types: " + Arrays.toString(types.toArray()));
            double method = jobNode.node("method").getDouble();
            PicoJobsCommon.getLogger().finest("Method: " + method);
            double salary = jobNode.node("salary").getDouble();
            PicoJobsCommon.getLogger().finest("Salary: " + salary);
            double maxSalary = jobNode.node("max-salary").getDouble();
            PicoJobsCommon.getLogger().finest("MaxSalary: " + maxSalary);
            boolean requiresPermission = jobNode.node("require-permission").getBoolean();
            PicoJobsCommon.getLogger().finest("RequiresPermission: " + requiresPermission);
            double salaryFrequency = jobNode.node("salary-frequency").getDouble();
            PicoJobsCommon.getLogger().finest("SalaryFrequency: " + salaryFrequency);
            double methodFrequency = jobNode.node("method-frequency").getDouble();
            PicoJobsCommon.getLogger().finest("MethodFrequency: " + methodFrequency);
            String economy = jobNode.node("economy").getString();
            if(!economy.isEmpty()) {
                economy = economy.toUpperCase(Locale.ROOT);
            }
            PicoJobsCommon.getLogger().finest("Economy: " + economy);
            String workZone = jobNode.node("work-zone").getString();
            if(!workZone.isEmpty()) {
                workZone = workZone.toUpperCase(Locale.ROOT);
            }
            PicoJobsCommon.getLogger().finest("Work Zone: " + workZone);
            String workMessage = jobNode.node("work-message").getString();
            ConfigurationNode guiNode = jobNode.node("gui");
            int slot = guiNode.node("slot").getInt();
            String item = guiNode.node("item").getString();
            int itemData = guiNode.node("item-data").getInt();
            boolean enchanted = guiNode.node("enchanted").getBoolean();
            List<String> lore = guiNode.node("lore").getList(String.class);

            // CALCULATING OPTIONALS

            boolean useWhitelist = jobNode.node("use-whitelist").getBoolean();
            Map<Type, List<String>> whitelist = new HashMap<>();
            if(!jobNode.node("whitelist").empty()) {
                //TODO: CHECK IF THIS EVEN WORKS
                for(Object type : jobNode.node("whitelist").childrenMap().keySet()) {
                    whitelist.put(Type.getType((String)type), jobNode.node("whitelist", type).getList(String.class));
                }
            }

            Job job = new Job(jobid, displayname, tag, types, method, salary, maxSalary, requiresPermission, salaryFrequency, methodFrequency, economy, workZone, workMessage, slot, item, itemData, enchanted, lore, useWhitelist, whitelist);

            jobs.put(jobid, job);

            //TODO: Metrics for each job
    }
        return true;
}

    private void checkVersion() {
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
            String version = json.get("name").getAsString();
            version = version.replaceFirst("PicoJobs ", "");

            DefaultArtifactVersion pluginVersion = new DefaultArtifactVersion(PicoJobsCommon.getVersion());
            DefaultArtifactVersion lastestVersion = new DefaultArtifactVersion(version);
            String lastestPluginVersion = version;
            String downloadUrl = json.get("downloadUrl").getAsString();
            boolean isRunningInOld = lastestVersion.compareTo(pluginVersion) > 0;
            if(PicoJobsCommon.getVersion().endsWith("-DEV")) {
                isRunningInOld = !GitHubAPI.isTagLatest(version);
            }
            if(isRunningInOld) {
                PicoJobsCommon.getLogger().warning("Version: " + lastestVersion + " is out! You are still running version: " + pluginVersion);
                //TODO: Create an auto update for the bukkit version (and if possible for other platforms too)
                if(FileManager.getConfigNode().node("auto-update").getBoolean() && PicoJobsCommon.getPlatform() == Platform.BUKKIT) {
                    /*if(updatePlugin(downloadUrl)) {
                        PicoJobsCommon.getLogger().info("Updating the plugin to the latest version...");
                    } else {
                        PicoJobsCommon.getLogger().warning("An error occuried while updating the plugin.");
                    }*/
                }
            } else {
                PicoJobsCommon.getLogger().info("You are using the latest version of the plugin.");
            }
        } catch (Exception ex) {
            PicoJobsCommon.getLogger().warning("Could not get the latest version.");
            ex.printStackTrace();
        }
    }
}
