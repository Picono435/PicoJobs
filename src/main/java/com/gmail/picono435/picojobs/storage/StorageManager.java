package com.gmail.picono435.picojobs.storage;

import java.util.logging.Level;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.sql.MySqlStorage;

public class StorageManager {
	
	private StorageFactory storageFactory;
	
	public StorageFactory initializeStorageFactory() {
		String method = PicoJobsAPI.getSettingsManager().getStorageMethod();
		switch(method.toLowerCase()) {
		case("mysql"): {
			this.storageFactory = new MySqlStorage();
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
