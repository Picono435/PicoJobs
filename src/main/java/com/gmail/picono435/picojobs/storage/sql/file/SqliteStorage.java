package com.gmail.picono435.picojobs.storage.sql.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class SqliteStorage extends FlatfileStorageFactory {
	
	@Override
	protected boolean initializeStorage() throws Exception {		
		this.connectionConstructor = Class.forName("org.sqlite.jdbc4.JDBC4Connection").getConstructor(String.class, String.class, Properties.class);
		
		PreparedStatement stm = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `jobplayers` (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
    	stm.execute();
    	stm.close();
		return true;
	}

	@Override
	protected Connection getConnection() throws Exception {
		if(this.conn == null || this.conn.isClosed()) {
			this.conn = (Connection) this.connectionConstructor.newInstance("jdbc:sqlite:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("picojobs-sqlite"), PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("picojobs-sqlite").toString(), new Properties());
		}
		return this.conn;
	}
}
