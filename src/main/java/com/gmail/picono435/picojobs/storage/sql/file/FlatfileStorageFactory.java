package com.gmail.picono435.picojobs.storage.sql.file;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.logging.Level;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.storage.StorageFactory;

public abstract class FlatfileStorageFactory extends StorageFactory {
	
	protected Connection conn;
	protected Constructor<?> connectionConstructor;
	
	protected abstract Connection getConnection() throws Exception;
	
	@Override
	protected void destroyStorage() {
		try {
			this.connectionConstructor = null;
		} catch(Exception ex) {
			PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, "Error connecting to the storage. The plugin will not work correctly.");
			return;
		}
	}
	
	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("INSERT INTO `jobplayers` (`uuid`) VALUES (?)");
        	stm.setString(1, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}
	
	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `uuid` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		stm.close();
        		return true;
        	} else {
        		stm.close();
        		return false;
        	}
		}
	}
	
	@Override
	public String getJob(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `job` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		String result = rs.getString("job");
        		stm.close();
        		return result;
        	} else {
        		stm.close();
        		return null;
        	}
		}
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `method` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("method");
        		stm.close();
        		return result;
        	} else {
        		stm.close();
        		return 0;
        	}
		}
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `level` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("level");
        		stm.close();
        		return result;
        	} else {
        		stm.close();
        		return 0;
        	}
		}
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `is-working` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		boolean result = rs.getBoolean("is-working");
        		stm.close();
        		return result;
        	} else {
        		stm.close();
        		return false;
        	}
		}
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("SELECT `salary` FROM `jobplayers` WHERE `uuid`=?");
        	stm.setString(1, uuid.toString());
        	ResultSet rs = stm.executeQuery();
        	if(rs.next()) {
        		double result = rs.getDouble("salary");
        		stm.close();
        		return result;
        	} else {
        		stm.close();
        		return 0;
        	}
		}
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE `jobplayers` SET `job`=? WHERE `uuid`=?");
        	stm.setString(1, job);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE `jobplayers` SET `method`=? WHERE `uuid`=?");
        	stm.setDouble(1, method);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE `jobplayers` SET `level`=? WHERE `uuid`=?");
        	stm.setDouble(1, level);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE `jobplayers` SET `is-working`=? WHERE `uuid`=?");
        	stm.setBoolean(1, isWorking);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		try(Connection conn = getConnection()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE `jobplayers` SET `salary`=? WHERE `uuid`=?");
        	stm.setDouble(1, salary);
        	stm.setString(2, uuid.toString());
        	int result = stm.executeUpdate();
        	stm.close();
        	return result >= 1;
		}
	}
	
}
