package com.gmail.picono435.picojobs.storage.sql.file;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class H2Storage extends SqlStorageFactory {
	
	@Override
	protected boolean initializeStorage() throws Exception {		
		Constructor<?> constructor = Class.forName("org.h2.jdbc.JdbcConnection").getConstructor(String.class, Properties.class);
		this.conn = (Connection) constructor.newInstance("jbdc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().getPath().toString(), new Properties());
		
		PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ? (`uuid` VARCHAR(255) NOT NULL, `job` TEXT, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN, PRIMARY KEY (`uuid`));");
    	stm.setString(1, configurationSection.getString("tablename"));
    	stm.execute();
    	stm.close();
		return true;
	}

}
