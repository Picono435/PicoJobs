package com.gmail.picono435.picojobs.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.storage.StorageFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class HikariStorageFactory extends StorageFactory {
	
	protected ConfigurationSection configurationSection;
	protected HikariDataSource hikari;
	protected HikariConfig config;
	
	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("INSERT INTO " + configurationSection.getString("tablename") +  " (`uuid`) VALUES (?)");
        	stm.setString(1, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}
	
	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `uuid` FROM " + configurationSection.getString("tablename") +  " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		stm.close();
        		conn.close();
        		return true;
        	} else {
        		stm.close();
        		conn.close();
        		return false;
        	}
		}
	}
	
	@Override
	public String getJob(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `job` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		String result = rs.getString("job");
        		stm.close();
        		conn.close();
        		return result;
        	} else {
        		stm.close();
        		conn.close();
        		return null;
        	}
		}
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `method` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("method");
        		stm.close();
        		conn.close();
        		return result;
        	} else {
        		stm.close();
        		conn.close();
        		return 0;
        	}
		}
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `level` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("level");
        		stm.close();
        		conn.close();
        		return result;
        	} else {
        		stm.close();
        		conn.close();
        		return 0;
        	}
		}
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `is-working` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		boolean result = rs.getBoolean("is-working");
        		stm.close();
        		conn.close();
        		return result;
        	} else {
        		stm.close();
        		conn.close();
        		return false;
        	}
		}
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `salary` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("salary");
        		stm.close();
        		conn.close();
        		return result;
        	} else {
        		stm.close();
        		conn.close();
        		return 0;
        	}
		}
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `job`=? WHERE `uuid`=?");
        	stm.setString(1, job);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `method`=? WHERE `uuid`=?");
        	stm.setDouble(1, method);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `level`=? WHERE `uuid`=?");
        	stm.setDouble(1, level);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `is-working`=? WHERE `uuid`=?");
        	stm.setBoolean(1, isWorking);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		try(Connection conn = hikari.getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `salary`=? WHERE `uuid`=?");
        	stm.setDouble(1, salary);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	conn.close();
        	return result >= 1;
		}
	}
	
}
