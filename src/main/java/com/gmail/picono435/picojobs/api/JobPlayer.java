package com.gmail.picono435.picojobs.api;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.events.PlayerEnterJobEvent;
import com.gmail.picono435.picojobs.api.events.PlayerFinishWorkEvent;
import com.gmail.picono435.picojobs.api.events.PlayerLeaveJobEvent;
import com.gmail.picono435.picojobs.api.events.PlayerStartWorkEvent;
import com.gmail.picono435.picojobs.api.events.PlayerWithdrawEvent;

/**
 * Represents a player from PicoJobs
 * 
 * @author Picono435
 *
 */
public class JobPlayer {
	
	private UUID uuid;
	private Job job;
	private double method;
	private double level;
	private boolean isWorking;
	private double salary;
	private String errorMessage;
	
	public JobPlayer(Job job, double method, double level, double salary, boolean isWorking, UUID uuid) {
		this.job = job;
		this.method = method;
		this.level = level;
		this.salary = salary;
		this.isWorking = isWorking;
		this.uuid = uuid;
		this.errorMessage = "Error connecting to the storage. The plugin will not work correctly.";
	}
	
	/**
	 * Gets the UUID of the player
	 * 
	 * @return the uuid
	 * @author Picono435
	 *
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Check if a player has a job.
	 * 
	 * @return true if has job, false if doesn't have
	 * @author Picono435
	 *
	 */
	public boolean hasJob() {
		return this.job != null;
	}
	
	/**
	 * Gets the job of a player.
	 * 
	 * @return null if player does not have job, the job if it does
	 * @author Picono435
	 *
	 */
	public Job getJob() {
		return this.job;
	}
	
	/**
	 * Sets the job of a player
	 * 
	 * @param job The Job
	 * @author Picono435
	 *
	 */
	public void setJob(Job job) {
		if(job == null) {
			PlayerLeaveJobEvent event = new PlayerLeaveJobEvent(this, Bukkit.getPlayer(uuid), job);
			Bukkit.getPluginManager().callEvent(event);
		}
		PlayerEnterJobEvent event = new PlayerEnterJobEvent(this, Bukkit.getPlayer(uuid), job);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) {
			return;
		}
		
		this.job = job;
		
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			try {
				if(job == null) {
					PicoJobsAPI.getStorageManager().getStorageFactory().setJob(uuid, null);
				} else {
					PicoJobsAPI.getStorageManager().getStorageFactory().setJob(uuid, job.getID());
				}
			} catch (Exception e) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, errorMessage);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Get the current method (broken blocks, fish caugh etc..) from a player
	 * 
	 * @return the method
	 * @author Picono435
	 *
	 */
	public double getMethod() {
		return this.method;
	}
	
	/**
	 * Sets the current method (broken blocks, fish caugh etc..) of a player
	 * 
	 * @param method The method
	 * @author Picono435
	 *
	 */
	public void setMethod(double method) {
		this.method = method;
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			try {
				PicoJobsAPI.getStorageManager().getStorageFactory().setMethod(uuid, method);
			} catch (Exception e) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, errorMessage);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Get the current method level (amount of works done) from a player
	 * 
	 * @return the method
	 * @author Picono435
	 *
	 */
	public double getMethodLevel() {
		return this.level;
	}
	
	/**
	 * Sets the current method level (amount of works done) of a player
	 * 
	 * @param level the level
	 * @author Picono435
	 *
	 */
	public void setMethodLevel(double level) {
		this.level = level;
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			try {
				PicoJobsAPI.getStorageManager().getStorageFactory().setMethodLevel(uuid, level);
			} catch (Exception e) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, errorMessage);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Check if a player has accepted a work or not
	 * 
	 * @return true if it is working, false if not
	 * @author Picono435
	 *
	 */
	public boolean isWorking() {
		return this.isWorking;
	}
	
	/**
	 * Set if the player is working or not
	 * 
	 * @param isWorking if it's working or not
	 * @author Picono435
	 *
	 */
	public void setWorking(boolean isWorking) {
		if(isWorking) {
			PlayerStartWorkEvent event = new PlayerStartWorkEvent(this, Bukkit.getPlayer(uuid), getJob());
			Bukkit.getPluginManager().callEvent(event);
		}
		
		this.isWorking = isWorking;
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			try {
				PicoJobsAPI.getStorageManager().getStorageFactory().setWorking(uuid, isWorking);
			} catch (Exception e) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, errorMessage);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Gets the current salary of a player
	 * 
	 * @return the salary
	 * @author Picono435
	 *
	 */
	public double getSalary() {
		return this.salary;
	}
	
	/**
	 * Sets the current salary of a player
	 * 
	 * @param salary the salary to set
	 * @author Picono435
	 *
	 */
	public void setSalary(double salary) {
		this.salary = salary;
		Bukkit.getScheduler().runTaskAsynchronously(PicoJobsPlugin.getInstance(), () -> {
			try {
				PicoJobsAPI.getStorageManager().getStorageFactory().setSalary(uuid, salary);
			} catch (Exception e) {
				PicoJobsPlugin.getInstance().sendConsoleMessage(Level.SEVERE, errorMessage);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Adds some salary from the current salary of a player
	 * 
	 * @param salary the salary to add
	 * @author Picono435
	 *
	 */
	public void addSalary(double salary) {
		setSalary(getSalary() + salary);
	}
	
	/**
	 * Removes some salary from the current salary of a player
	 * 
	 * @param salary the salary to remove
	 * @author Picono435
	 *
	 */
	public void removeSalary(double salary) {
		PlayerWithdrawEvent event = new PlayerWithdrawEvent(this, Bukkit.getPlayer(uuid), salary);
		Bukkit.getPluginManager().callEvent(event);
		setSalary(getSalary() - salary);
	}
	
	/**
	 * Simulates an event, for example it will simulate a caught fish or a break block from the plugin.
	 * 
	 * @return true if it will level up, false if not
	 * @author Picono435
	 *
	 */
	public boolean simulateEvent() {
		if(!isWorking()) return false;
		double level = getMethodLevel();
		double method = getMethod();
		setMethod(method + 1);

		double req1 = level * getJob().getMethodFrequency();
		if(req1 <= 0) req1 = 1;
		int reqmethod = (int) (getJob().getMethod() * req1);
		if(reqmethod == 0) reqmethod = 1;
		
		if(getMethod() >= reqmethod) {
			PlayerFinishWorkEvent event = new PlayerFinishWorkEvent(this, Bukkit.getPlayer(uuid), getJob());
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return false;
			}
			double sal1 = level * getJob().getSalaryFrequency();
			if(sal1 <= 0) sal1 = 1;
			double salary = getJob().getSalary() * sal1;
			if(salary > getJob().getMaxSalary()) {
				salary = getJob().getMaxSalary();
			}
			setMethodLevel(level + 1);
			setMethod(0);
			setWorking(false);
			setSalary(getSalary() + salary);
			return true;
		}
		return false;
	}
	
	/**
	 * Resets completly the player, including the job, if its working the method and etc... but keeps half of the salary
	 * 
	 * @author Picono435
	 *
	 */
	public void removePlayerStats() {
		setWorking(false);
		setSalary(getSalary() / 2);
		setMethod(0);
		setMethodLevel(1);
		setJob(null);
	}
}
