package com.gmail.picono435.picojobs.storage.sql.file;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class SqliteStorage extends SqlStorageFactory {
	
	@Override
	protected boolean initializeStorage() throws Exception {		
		Constructor<?> constructor = Class.forName("org.sqlite.jdbc4.JDBC4Connection").getConstructor(String.class, String.class, Properties.class);
		this.conn = (Connection) constructor.newInstance("jdbc:sqlite:" + PicoJobsPlugin.getInstance().getDataFolder().getPath().toString(), PicoJobsPlugin.getInstance().getDataFolder().getPath().toString(), new Properties());
		return true;
	}

}
