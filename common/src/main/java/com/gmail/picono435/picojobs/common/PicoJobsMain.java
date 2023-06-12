package com.gmail.picono435.picojobs.common;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.util.*;

public class PicoJobsMain {

    private boolean wasUpdated;
    private String serverVersion;
    private boolean oldVersion;
    private String lastestPluginVersion;
    private String downloadUrl;

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
        if(!generateJobsFromConfig()) return;


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
}
