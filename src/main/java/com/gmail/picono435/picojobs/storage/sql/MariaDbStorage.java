package com.gmail.picono435.picojobs.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class MariaDbStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws SQLException {
		configurationSection = PicoJobsAPI.getSettingsManager().getMySQLConfiguration();
		String address = configurationSection.getString("host");
		String port = configurationSection.getString("port");
		String databaseName = configurationSection.getString("database");
		String username = configurationSection.getString("username");
		String password = configurationSection.getString("password");
		
		config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("serverName", address);
        config.addDataSourceProperty("port", port);
        config.addDataSourceProperty("databaseName", databaseName);
        config.setUsername(username);
        config.setPassword(password);
        
        this.hikari = new HikariDataSource(config);
        
        try(Connection conn = hikari.getConnection()) {
        	PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ? (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.execute();
        	stm.close();
        	conn.close();
        }
		return false;
	}
	
	@Override
	public void destroyStorage() {
		hikari.close();
		return;
	}
}
