package com.gmail.picono435.picojobs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;

public class MySQLAPI {
	public Connection connection;
	private ConfigurationSection config;
	private String tablename = "jobplayers";
    
	public boolean startConnection() {
		try {
            openConnection();
            createTable();
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }
	}
	
    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
     
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            
            config = PicoJobsAPI.getSettingsManager().getMySQLConfiguration();
            String host = config.getString("host");
            int port = config.getInt("port");
            String database = config.getString("database");
            String username = config.getString("username");
            String password = config.getString("password");
            if(password.equals("")) {
            	password = null;
            }
            
            connection = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
        }
    }
    
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
            	e.printStackTrace();
            }
        }
    }
    
    public void createTable() {
        if (connection != null) {
            PreparedStatement stm = null;
            try {
            	tablename = config.getString("tablename");
                stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + tablename + "` (`uuid` TEXT NOT NULL, `job` TEXT, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN);");
                stm.executeUpdate();
            }
            catch (SQLException e) {
            	e.printStackTrace();
            }
        }
    }
    
    public void addINDB(final String uuid, final String job, final double method, final double level, final double salary, final boolean isWorking) {
    	PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("INSERT INTO `" + tablename + "` (`uuid`, `job`, `method`, `level`, `salary`, `is-working`) VALUES (?,?,?,?,?,?)");
            stm.setString(1, uuid);
            stm.setString(2, job);
            stm.setDouble(3, method);
            stm.setDouble(4, level);
            stm.setDouble(5, salary);
            stm.setBoolean(6, isWorking);
            stm.executeUpdate();
        }
        catch (SQLException ex) {
        	 ex.printStackTrace();
        }
    }
    
    public JobPlayer getFromDB(final String uuid) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `" + tablename + "` WHERE `uuid` = ?");
            stm.setString(1, uuid);
            final ResultSet rs = stm.executeQuery();
            if (rs.next()) {
            	String job = rs.getString("job");
            	double method = rs.getDouble("method");
            	double level = rs.getDouble("level");
            	double salary = rs.getDouble("salary");
            	boolean isWorking = rs.getBoolean("is-working");
                return new JobPlayer(PicoJobsAPI.getJobsManager().getJob(job), method, level, salary, isWorking, UUID.fromString(uuid));
            }
            return null;
        }
        catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<String> getAllUsers() {
        final ArrayList<String> list = new ArrayList<String>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `" + tablename + "`");
            final ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("uuid"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void deleteMysqlRecords() {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("DELETE FROM `" + tablename + "`");
            stm.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
