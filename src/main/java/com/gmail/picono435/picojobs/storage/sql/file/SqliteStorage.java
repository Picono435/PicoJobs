package com.gmail.picono435.picojobs.storage.sql.file;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class SqliteStorage extends SqlStorageFactory {
	
	@Override
	protected boolean initializeStorage() throws Exception {		
		Constructor<?> constructor = Class.forName("org.sqlite.jdbc4.JDBC4Connection").getConstructor(String.class, String.class, Properties.class);
		this.conn = (Connection) constructor.newInstance("jdbc:sqlite:" + PicoJobsPlugin.getInstance().getDataFolder().getPath().toString(), PicoJobsPlugin.getInstance().getDataFolder().getPath().toString(), new Properties());
		
		PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ? (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
    	stm.setString(1, configurationSection.getString("tablename"));
    	stm.execute();
    	stm.close();
		return true;
	}

}
