package com.gmail.picono435.picojobs.storage.sql.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class H2Storage extends FlatfileStorageFactory {
	
	@Override
	protected boolean initializeStorage() throws Exception {
		this.connectionConstructor = Class.forName("org.h2.jdbc.JdbcConnection").getConstructor(String.class, Properties.class);
		
		PreparedStatement stm = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `jobplayers` (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
    	stm.execute();
    	stm.close();
		return true;
	}

	@Override
	protected Connection getConnection() throws Exception {
		return (Connection) this.connectionConstructor.newInstance("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("picojobs-h2"), new Properties());
	}
}