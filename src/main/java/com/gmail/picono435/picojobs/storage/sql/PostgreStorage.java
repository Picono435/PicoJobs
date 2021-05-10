package com.gmail.picono435.picojobs.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class PostgreStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws SQLException {
		configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();
		String address = configurationSection.getString("host");
		String port = configurationSection.getString("port");
		String databaseName = configurationSection.getString("database");
		String username = configurationSection.getString("username");
		String password = configurationSection.getString("password");
		
		config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.addDataSourceProperty("serverName", address);
        config.addDataSourceProperty("portNumber", port);
        config.addDataSourceProperty("databaseName", databaseName);
        config.addDataSourceProperty("user", username);
        config.addDataSourceProperty("password", password);
        
        this.hikari = new HikariDataSource(config);
        
        try(Connection conn = hikari.getConnection()) {
        	PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + configurationSection.getString("tablename") + " (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
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
