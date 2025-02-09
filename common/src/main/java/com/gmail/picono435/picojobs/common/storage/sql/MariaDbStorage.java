package com.gmail.picono435.picojobs.common.storage.sql;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class MariaDbStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws Exception {
		configurationNode = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();
		String address = configurationNode.node("host").getString();
		String port = configurationNode.node("port").getString();
		String databaseName = configurationNode.node("database").getString();
		String username = configurationNode.node("username").getString();
		String password = configurationNode.node("password").getString();

		config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("url", String.format("jdbc:mariadb://%s:%s/%s", address, port, databaseName));
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
