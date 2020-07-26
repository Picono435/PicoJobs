package com.gmail.picono435.picojobs.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class FileCreator {
	
	private static FileConfiguration data;
	private static File data_file;
	
	public static boolean generateFiles() {
		return createDataFile();
	}
	
	public static FileConfiguration getData() {
		return data;
	}
	
	public static File getDataFile() {
		return data_file;
	}
	
	public static boolean createDataFile() {
    	data_file = new File(PicoJobsPlugin.getPlugin().getDataFolder(), "data.yml");
        if (!data_file.exists()) {
        	data_file.getParentFile().mkdirs();
        	PicoJobsPlugin.getPlugin().saveResource("data.yml", false);
         }

        data = new YamlConfiguration();
        try {
            data.load(data_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
