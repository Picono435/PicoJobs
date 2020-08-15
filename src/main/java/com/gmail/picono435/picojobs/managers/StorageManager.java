package com.gmail.picono435.picojobs.managers;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.StorageMethod;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.MySQLAPI;

public class StorageManager {
	
	public StorageMethod storageMethod = StorageMethod.YAML;
	
	public void getData() {
		storageMethod = StorageMethod.getStorageMethod(PicoJobsPlugin.getPlugin().getConfig().getConfigurationSection("storage").getString("storage-method"));
		
		if(storageMethod == StorageMethod.YAML) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Using the YAML storage method.");
			getDataInConfig();
			return;
		}
		
		if(storageMethod == StorageMethod.MYSQL) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Using the MySQL storage method.");
			getDataInMySQL();
			return;
		}
		
		PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] We did not find any storage method with that name. Using YAML storage method as default.");
		getDataInConfig();
	}
	
	private void getDataInMySQL() {
		MySQLAPI api = new MySQLAPI();
		api.startConnection();
		for(String uuid : api.getAllUsers()) {
			System.out.println(uuid);
			PicoJobsPlugin.playersdata.put(UUID.fromString(uuid), api.getFromDB(uuid));
		}
		api.close();
	}
	
	private void getDataInConfig() {
		FileCreator.createDataFile();
		FileConfiguration data = FileCreator.getData();
		if(data.getConfigurationSection("playerdata") == null) return;
		for(String uuid : data.getConfigurationSection("playerdata").getKeys(false)) {
			if(uuid.equals("none")) continue;
			ConfigurationSection playerCategory = data.getConfigurationSection("playerdata").getConfigurationSection(uuid);
			Job job = PicoJobsAPI.getJobsManager().getJob(playerCategory.getString("job"));
			double method = playerCategory.getDouble("method");
			double level = playerCategory.getDouble("level");
			double salary = playerCategory.getDouble("salary");
			boolean isWorking = playerCategory.getBoolean("is-working");
			JobPlayer jp = new JobPlayer(job, method, level, salary, isWorking);
			PicoJobsPlugin.playersdata.put(UUID.fromString(uuid), jp);
		}
	}
	
	public void saveData() {
		storageMethod = StorageMethod.getStorageMethod(PicoJobsPlugin.getPlugin().getConfig().getConfigurationSection("storage").getString("storage-method"));
		
		if(storageMethod == StorageMethod.YAML) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Using the YAML storage method.");
			saveInConfig();
			return;
		}
		
		if(storageMethod == StorageMethod.MYSQL) {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Using the MySQL storage method.");
			saveInMySQL();
			return;
		}
		
		PicoJobsPlugin.sendConsoleMessage(ChatColor.YELLOW + "[PicoJobs] We did not find any storage method with that name. Using YAML storage method as default.");
		saveInConfig();
	}
	
	private void saveInMySQL() {
		MySQLAPI api = new MySQLAPI();
		api.startConnection();
		api.deleteMysqlRecords();
		for(UUID uuid : PicoJobsPlugin.playersdata.keySet()) {
			JobPlayer jp = PicoJobsPlugin.playersdata.get(uuid);
			api.addINDB(uuid.toString(), jp.getJob().getName(), jp.getMethod(), jp.getMethodLevel(), jp.getSalary(), jp.isWorking());
		}
		api.close();
	}
	
	private void saveInConfig() {
		if(FileCreator.getDataFile() != null) {
			FileCreator.getDataFile().delete();
		}
		if(!FileCreator.createDataFile()) return;
		ConfigurationSection playerDataCategory = FileCreator.getData().getConfigurationSection("playerdata");
		for(UUID uuid : PicoJobsPlugin.playersdata.keySet()) {
			JobPlayer jp = PicoJobsPlugin.playersdata.get(uuid);
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
	}
}
