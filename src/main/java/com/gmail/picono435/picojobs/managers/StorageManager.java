package com.gmail.picono435.picojobs.managers;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.StorageMethod;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.utils.MongoDBAPI;
import com.gmail.picono435.picojobs.utils.MySQLAPI;

public class StorageManager {
	
	public StorageMethod storageMethod = StorageMethod.YAML;
	
	/*
	 * GET DATA METHODS 
	 */
	// GENERAL
	public void getData() {
		storageMethod = StorageMethod.getStorageMethod(PicoJobsAPI.getSettingsManager().getStorageMethod());
		
		if(storageMethod == StorageMethod.YAML) {
			getDataInConfig();
			return;
		}
		
		if(storageMethod == StorageMethod.MYSQL) {
			getDataInMySQL();
			return;
		}
		
		if(storageMethod == StorageMethod.MONGODB) {
			getDataInMongoDB();
			return;
		}
		
		PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "We did not find any storage method with that name. Using YAML storage method as default.");
		getDataInConfig();
	}
	
	// YAML
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
			JobPlayer jp = new JobPlayer(job, method, level, salary, isWorking, UUID.fromString(uuid));
			PicoJobsPlugin.getInstance().playersdata.put(UUID.fromString(uuid), jp);
		}
	}
	
	// MYSQL
	private void getDataInMySQL() {
		MySQLAPI api = new MySQLAPI();
		api.startConnection();
		for(String uuid : api.getAllUsers()) {
			PicoJobsPlugin.getInstance().playersdata.put(UUID.fromString(uuid), api.getFromDB(uuid));
		}
		api.close();
	}
	
	// MYSQL
	private void getDataInMongoDB() {
		MongoDBAPI api = new MongoDBAPI();
		api.startConnection();
		for(String uuid : api.getAllUsers()) {
			PicoJobsPlugin.getInstance().playersdata.put(UUID.fromString(uuid), api.getFromDB(uuid));
		}
		api.close();
	}
	
	/*
	 * SAVE DATA METHODS 
	 */
	// GENERAL
	public void saveData() {
		storageMethod = StorageMethod.getStorageMethod(PicoJobsAPI.getSettingsManager().getStorageMethod());
		
		if(storageMethod == StorageMethod.YAML) {
			saveInConfig();
			return;
		}
		
		if(storageMethod == StorageMethod.MYSQL) {
			saveInMySQL();
			return;
		}
		
		if(storageMethod == StorageMethod.MONGODB) {
			saveInMongoDB();
			return;
		}
		
		PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "We did not find any storage method with that name. Using YAML storage method as default.");
		saveInConfig();
	}
	
	// YAML
	private void saveInConfig() {
		ConfigurationSection playerDataCategory = FileCreator.getData().getConfigurationSection("playerdata");
		for(UUID uuid : PicoJobsPlugin.getInstance().playersdata.keySet()) {
			JobPlayer jp = PicoJobsPlugin.getInstance().playersdata.get(uuid);
			ConfigurationSection player = playerDataCategory.getConfigurationSection(uuid.toString());
			if(player == null) {
				player = playerDataCategory.createSection(uuid.toString());
			}
			if(jp.getJob() == null) {
				player.set("job", null);
			} else {
				player.set("job", jp.getJob().getID());
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
	
	// MYSQL
	private void saveInMySQL() {
		MySQLAPI api = new MySQLAPI();
		api.startConnection();
		api.deleteMysqlRecords();
		for(UUID uuid : PicoJobsPlugin.getInstance().playersdata.keySet()) {
			JobPlayer jp = PicoJobsPlugin.getInstance().playersdata.get(uuid);
			api.addINDB(uuid.toString(), jp.getJob().getID(), jp.getMethod(), jp.getMethodLevel(), jp.getSalary(), jp.isWorking());
		}
		api.close();
	}
	
	// MYSQL
	private void saveInMongoDB() {
		MongoDBAPI api = new MongoDBAPI();
		api.startConnection();
		api.deleteAllDocuments();
		for(UUID uuid : PicoJobsPlugin.getInstance().playersdata.keySet()) {
			JobPlayer jp = PicoJobsPlugin.getInstance().playersdata.get(uuid);
			api.addINDB(uuid.toString(), jp.getJob().getID(), jp.getMethod(), jp.getMethodLevel(), jp.getSalary(), jp.isWorking());
		}
		api.close();
	}
}
