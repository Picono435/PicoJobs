package com.gmail.picono435.picojobs.common.storage;

import java.util.Locale;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.storage.cache.CacheManager;
import com.gmail.picono435.picojobs.common.storage.file.JsonStorage;
import com.gmail.picono435.picojobs.common.storage.file.YamlStorage;
import com.gmail.picono435.picojobs.common.storage.sql.*;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.storage.file.HoconStorage;
import com.gmail.picono435.picojobs.common.storage.mongodb.MongoStorage;

public class StorageManager {
	
	private CacheManager cacheManager;
	private StorageFactory storageFactory;
	
	public StorageManager() {
		this.cacheManager = new CacheManager();
	}
	
	public StorageFactory initializeStorageFactory() {
		StorageType storageType = StorageType.fromString(PicoJobsAPI.getSettingsManager().getStorageMethod());
		switch(storageType) {
		case MYSQL: {
			this.storageFactory = new MySqlStorage();
			break;
		}
		case MARIADB: {
			this.storageFactory = new MariaDbStorage();
			break;
		}
		case POSTGRE: {
			this.storageFactory = new PostgreStorage();
			break;
		}
		case MONGODB: {
			this.storageFactory = new MongoStorage();
			break;
		}
		case H2: {
			this.storageFactory = new H2Storage();
			break;
		}
		case SQLITE: {
			this.storageFactory = new SqliteStorage();
			break;
		}
		case YAML: {
			this.storageFactory = new YamlStorage();
			break;
		}
		case JSON: {
			this.storageFactory = new JsonStorage();
			break;
		}
		case HOCON: {
			this.storageFactory = new HoconStorage();
			break;
		}
		default: {
			PicoJobsCommon.getLogger().warn("A valid storage method was not selected, please be sure to select only avaiable storage methods. Using H2 as the default storage method.");
			this.storageFactory = new H2Storage();
			break;
		}
		}
		try {
			this.storageFactory.initializeStorage();
		} catch (Exception ex) {
			PicoJobsCommon.getLogger().error("Error connecting to the storage. The plugin will not work correctly.");
			ex.printStackTrace();
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
