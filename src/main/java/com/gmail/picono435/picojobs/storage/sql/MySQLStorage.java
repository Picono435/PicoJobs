package com.gmail.picono435.picojobs.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.StorageFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLStorage extends StorageFactory {

	private ConfigurationSection configurationSection;
	private HikariDataSource hikari;
	private HikariConfig config;
	
	@Override
	public boolean initializeStorage() throws SQLException {
		configurationSection = PicoJobsAPI.getSettingsManager().getMySQLConfiguration();
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
        
        try(Connection conn = hikari.getConnection()) {
        	PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ? (`uuid` VARCHAR(255) NOT NULL, `job` TEXT, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN, PRIMARY KEY (`uuid`));");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.execute();
        	stm.close();
        	conn.close();
        }
		return false;
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `job` FROM ? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	stm.close();
        	conn.close();
        	if(rs.next()) {
        		return rs.getString("job");
        	} else {
        		return null;
        	}
		}
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `method` FROM ? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	stm.close();
        	conn.close();
        	if(rs.next()) {
        		return rs.getDouble("method");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `level` FROM ? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	stm.close();
        	conn.close();
        	if(rs.next()) {
        		return rs.getDouble("level");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `is-working` FROM ? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	stm.close();
        	conn.close();
        	if(rs.next()) {
        		return rs.getBoolean("is-working");
        	} else {
        		return false;
        	}
		}
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `salary` FROM ? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	stm.close();
        	conn.close();
        	if(rs.next()) {
        		return rs.getDouble("salary");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE ? SET `job`=? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setString(2, job);
        	stm.setString(3, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE ? SET `method`=? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setDouble(2, method);
        	stm.setString(3, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE ? SET `level`=? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setDouble(2, level);
        	stm.setString(3, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE ? SET `is-working`=? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setBoolean(2, isWorking);
        	stm.setString(3, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE ? SET `salary`=? WHERE `uuid`=?");
        	stm.setString(1, configurationSection.getString("tablename"));
        	stm.setDouble(2, salary);
        	stm.setString(3, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}
	
	@Override
	public void destroyStorage() {
		hikari.close();
		return;
	}
}
