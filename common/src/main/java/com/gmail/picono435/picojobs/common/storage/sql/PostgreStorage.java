package com.gmail.picono435.picojobs.common.storage.sql;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class PostgreStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws Exception {
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

		this.createTable();
		return false;
	}
	
	@Override
	public void destroyStorage() {
		hikari.close();
	}
}
