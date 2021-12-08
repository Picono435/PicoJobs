package com.gmail.picono435.picojobs.storage.sql.file;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import org.codehaus.plexus.util.FileUtils;

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
		if(this.conn == null || this.conn.isClosed() || !this.conn.isValid(15)) {
			this.conn = (Connection) this.connectionConstructor.newInstance("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"), new Properties());
			Path scriptFile = PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("script").toAbsolutePath();
			if(scriptFile.toFile().exists()) {
				// Import data to the database
				PreparedStatement stm = conn.prepareStatement("RUNSCRIPT FROM ?");
				stm.setString(1, scriptFile.toString());
				stm.execute();
				stm.close();
				scriptFile.toFile().delete();
				this.conn = (Connection) this.connectionConstructor.newInstance("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"), new Properties());
			}
		}
		return this.conn;
	}
}