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
	protected HikariConfig config = new HikariConfig();
	
	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("INSERT INTO " + configurationSection.getString("tablename") +  " (`uuid`) VALUES (?)")) {
        	stm.setString(1, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}
	
	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `uuid` FROM " + configurationSection.getString("tablename") +  " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	return rs.next();
		}
	}
	
	@Override
	public String getJob(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `job` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
				return rs.getString("job");
        	} else {
        		return null;
        	}
		}
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `method` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		return rs.getDouble("method");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `level` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
				return rs.getDouble("level");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `is-working` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
				return rs.getBoolean("is-working");
        	} else {
        		return false;
        	}
		}
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `salary` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
				return rs.getDouble("salary");
        	} else {
        		return 0;
        	}
		}
	}

	@Override
	public long getSalaryCooldown(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `salary-cooldown` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
			stm.setString(1, uuid.toString());
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				return rs.getLong("salary-cooldown");
			} else {
				return 0;
			}
		}
	}

	@Override
	public long getLeaveCooldown(UUID uuid) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("SELECT `leave-cooldown` FROM " + configurationSection.getString("tablename") + " WHERE `uuid`=?")) {
			stm.setString(1, uuid.toString());
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				return rs.getLong("leave-cooldown");
			} else {
				return 0;
			}
		}
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `job`=? WHERE `uuid`=?")) {
        	stm.setString(1, job);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `method`=? WHERE `uuid`=?")) {
        	stm.setDouble(1, method);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `level`=? WHERE `uuid`=?")) {
        	stm.setDouble(1, level);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `is-working`=? WHERE `uuid`=?")) {
        	stm.setBoolean(1, isWorking);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `salary`=? WHERE `uuid`=?")) {
        	stm.setDouble(1, salary);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	return result >= 1;
		}
	}

	@Override
	public boolean setSalaryCooldown(UUID uuid, long salaryCooldown) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `salary-cooldown`=? WHERE `uuid`=?")) {
			stm.setLong(1, salaryCooldown);
			stm.setString(2, uuid.toString());
			int result = stm.executeUpdate();
			return result >= 1;
		}
	}

	@Override
	public boolean setLeaveCooldown(UUID uuid, long leaveCooldown) throws Exception {
		try(Connection conn = hikari.getConnection();
			PreparedStatement stm = conn.prepareStatement("UPDATE " + configurationSection.getString("tablename") + " SET `leave-cooldown`=? WHERE `uuid`=?")) {
			stm.setLong(1, leaveCooldown);
			stm.setString(2, uuid.toString());
			int result = stm.executeUpdate();
			return result >= 1;
		}
	}
	
}
