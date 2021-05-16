package com.gmail.picono435.picojobs.storage;

import java.util.logging.Level;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.cache.CacheManager;
import com.gmail.picono435.picojobs.storage.file.JsonStorage;
import com.gmail.picono435.picojobs.storage.file.YamlStorage;
import com.gmail.picono435.picojobs.storage.mongodb.MongoStorage;
import com.gmail.picono435.picojobs.storage.sql.MariaDbStorage;
import com.gmail.picono435.picojobs.storage.sql.MySqlStorage;
import com.gmail.picono435.picojobs.storage.sql.PostgreStorage;
import com.gmail.picono435.picojobs.storage.sql.file.H2Storage;
import com.gmail.picono435.picojobs.storage.sql.file.SqliteStorage;

public class StorageManager {
	
	private CacheManager cacheManager;
	private StorageFactory storageFactory;
	
	public StorageManager() {
		this.cacheManager = new CacheManager();
	}
	
	public StorageFactory initializeStorageFactory() {
		String method = PicoJobsAPI.getSettingsManager().getStorageMethod();
		switch(method.toLowerCase()) {
		case("mysql"): {
			this.storageFactory = new MySqlStorage();
			break;
		}
		case("mariadb"): {
			this.storageFactory = new MariaDbStorage();
			break;
		}
		case("postgre"): {
			this.storageFactory = new PostgreStorage();
			break;
		}
		case("mongodb"): {
			this.storageFactory = new MongoStorage();
			break;
		}
		case("h2"): {
			this.storageFactory = new H2Storage();
			break;
		}
		case("sqlite"): {
			this.storageFactory = new SqliteStorage();
			break;
		}
		case("yaml"): {
			this.storageFactory = new YamlStorage();
			break;
		}
		case("json"): {
			this.storageFactory = new JsonStorage();
			break;
		}
		default: {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.WARNING, "A valid storage method was not selected, please be sure to select only avaiable storage methods. Using H2 as the default storage method.");
			this.storageFactory = new H2Storage();
			break;
		}
		}
		try {
			this.storageFactory.initializeStorage();
		} catch (Exception e) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, "Error connecting to the storage. The plugin will not work correctly.");
			e.printStackTrace();
		}
		return storageFactory;
	}
	
	public StorageFactory getStorageFactory() {
		return this.storageFactory;	
	}
	
	public CacheManager getCacheManager() {
		return this.cacheManager;
	}
	
	public void destroyStorageFactory() {
		this.storageFactory.destroyStorage();
	}
}
