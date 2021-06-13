package com.gmail.picono435.picojobs.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class FileCreator {
	
	private static FileConfiguration gui;
	private static File gui_file;
	
	private static FileConfiguration jobs;
	private static File jobs_file;
	
	public static boolean generateFiles() {
		createGUIFile();
		createJobsFile();
		return true;
	}
	
	public static boolean reloadFiles() {
		try {
			gui = YamlConfiguration.loadConfiguration(gui_file);
			jobs = YamlConfiguration.loadConfiguration(jobs_file);
			PicoJobsPlugin.getInstance().reloadConfig();
			PicoJobsAPI.getSettingsManager().reloadConfigurations();
			LanguageManager.reloadConfigurations();
			PicoJobsPlugin.getInstance().generateJobsFromConfig();
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static void migrateFiles() {
		try {
			String[] oldMetsName = {"blocks", "kills", "fish", "items", "entities", "buckets"};
			String[] oldWhitName = {"block", "job", "item", "entity", "color", "liquid"};
			
			for(String job : jobs.getConfigurationSection("jobs").getKeys(false)) {
				if(!jobs.contains("jobs." + job + ".max-salary")) {
					jobs.set("jobs." + job + ".max-salary", 50000);
				}
				for(String met : oldMetsName) {
					if(jobs.contains(met)) {
						int value = jobs.getInt(met);
						jobs.set(met, null);
						jobs.set("method", value);
						break;
					}
				}
				for(String whit : oldWhitName) {
					if(jobs.contains(whit + "-whitelist")) {
						List<String> value = jobs.getStringList(whit + "-whitelist");
						jobs.set(whit + "-whitelist", null);
						jobs.set("whitelist", value);
						break;
					}
				}
			}
			jobs.save(jobs_file);
		} catch(Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
	
	public static boolean reloadGUIFile() {
		try {
			gui = YamlConfiguration.loadConfiguration(gui_file);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/*
	 * GUIs file
	 */
	public static FileConfiguration getGUI() {
		return gui;
	}
	
	public static File getGUIFile() {
		return gui_file;
	}
	
	public static boolean createGUIFile() {
		gui_file = new File(PicoJobsPlugin.getInstance().getDataFolder(), "settings" + File.separatorChar + "guis.yml");
        if (!gui_file.exists()) {
        	gui_file.getParentFile().mkdirs();
        	PicoJobsPlugin.getInstance().saveResource("settings" + File.separatorChar + "guis.yml", false);
         }

        gui = new YamlConfiguration();
        try {
        	gui.load(gui_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	/*
	 * Jobs file
	 */
	public static FileConfiguration getJobsConfig() {
		return jobs;
	}
	
	public static File getJobsFile() {
		return jobs_file;
	}
	
	public static boolean createJobsFile() {
		jobs_file = new File(PicoJobsPlugin.getInstance().getDataFolder(), "settings" + File.separatorChar + "jobs.yml");
        if (!jobs_file.exists()) {
        	jobs_file.getParentFile().mkdirs();
        	PicoJobsPlugin.getInstance().saveResource("settings" + File.separatorChar + "jobs.yml", false);
         }

        jobs = new YamlConfiguration();
        try {
        	jobs.load(jobs_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
