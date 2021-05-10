package com.gmail.picono435.picojobs.storage;

import java.util.logging.Level;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.mongodb.MongoStorage;
import com.gmail.picono435.picojobs.storage.sql.MariaDbStorage;
import com.gmail.picono435.picojobs.storage.sql.MySqlStorage;
import com.gmail.picono435.picojobs.storage.sql.PostgreStorage;
import com.gmail.picono435.picojobs.storage.sql.file.H2Storage;
import com.gmail.picono435.picojobs.storage.sql.file.SqliteStorage;

public class StorageManager {
	
	private StorageFactory storageFactory;
	
	public StorageFactory initializeStorageFactory() {
		String method = PicoJobsAPI.getSettingsManager().getStorageMethod();
		switch(method.toLowerCase()) {
		case("mysql"): {
			this.storageFactory = new MySqlStorage();
		}
		case("mariadb"): {
			this.storageFactory = new MariaDbStorage();
		}
		case("postgre"): {
			this.storageFactory = new PostgreStorage();
		}
		case("h2"): {
			this.storageFactory = new H2Storage();
		}
		case("sqlite"): {
			this.storageFactory = new SqliteStorage();
		}
		case("mongodb"): {
			this.storageFactory = new MongoStorage();
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
	
	public void destroyStorageFactory() {
		this.storageFactory.destroyStorage();
	}
}
