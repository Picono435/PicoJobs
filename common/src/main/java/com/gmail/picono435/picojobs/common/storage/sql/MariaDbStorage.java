package com.gmail.picono435.picojobs.common.storage.sql;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class MariaDbStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws Exception {
		configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();
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

		this.createTable();
		return false;
	}
	
	@Override
	public void destroyStorage() {
		hikari.close();
	}
}
