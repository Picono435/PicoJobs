package com.gmail.picono435.picojobs.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class MySqlStorage extends HikariStorageFactory {
	
	@Override
	public boolean initializeStorage() throws SQLException {
		configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();
		String address = configurationSection.getString("host");
		String port = configurationSection.getString("port");
		String databaseName = configurationSection.getString("database");
		String username = configurationSection.getString("username");
		String password = configurationSection.getString("password");
		
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + databaseName);
        config.setUsername(username);
        config.setPassword(password);
        
        this.hikari = new HikariDataSource(config);

		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + configurationSection.getString("tablename") + " (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `salary-cooldown` LONG DEFAULT '0', `leave-cooldown` LONG DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));")) {
			stm.execute();
		}

		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("ALTER TABLE " + configurationSection.getString("tablename") + " ADD COLUMN IF NOT EXISTS `salary-cooldown` LONG DEFAULT '0';")) {
			stm.execute();
		}
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("ALTER TABLE " + configurationSection.getString("tablename") + " ADD COLUMN IF NOT EXISTS `leave-cooldown` LONG DEFAULT '0';")) {
			stm.execute();
		}
		return false;
	}
	
	@Override
	public void destroyStorage() {
		hikari.close();
	}
}
