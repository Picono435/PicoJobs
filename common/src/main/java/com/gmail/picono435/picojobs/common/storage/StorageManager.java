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
		String method = PicoJobsAPI.getSettingsManager().getStorageMethod();
		switch(method.toLowerCase(Locale.ROOT)) {
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
		case("hocon"): {
			this.storageFactory = new HoconStorage();
			break;
		}
		default: {
			PicoJobsCommon.getLogger().warning("A valid storage method was not selected, please be sure to select only avaiable storage methods. Using H2 as the default storage method.");
			this.storageFactory = new H2Storage();
			break;
		}
		}
		try {
			this.storageFactory.initializeStorage();
		} catch (Exception e) {
			PicoJobsCommon.getLogger().severe("Error connecting to the storage. The plugin will not work correctly.");
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
