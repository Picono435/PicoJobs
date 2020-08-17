package com.gmail.picono435.picojobs.api;

/**
 * Represents a player from PicoJobs
 * 
 * @author Picono435
 *
 */
public class JobPlayer {
	
	private Job job;
	private double method;
	private double level;
	private boolean isWorking;
	private double salary;
	
	public JobPlayer(Job job, double method, double level, double salary, boolean isWorking) {
		this.job = job;
		this.method = method;
		this.level = level;
		this.salary = salary;
		this.isWorking = isWorking;
	}
	
	/**
	 * Check if a player has a job.
	 * 
	 * @return true if has job, false if doesn't have
	 * @author Picono435
	 *
	 */
	public boolean hasJob() {
		return job != null;
	}
	
	/**
	 * Gets the job of a player.
	 * 
	 * @return null if player does not have job, the job if it does
	 * @author Picono435
	 *
	 */
	public Job getJob() {
		return job;
	}
	
	/**
	 * Get the current method (broken blocks, fish caugh etc..) from a player
	 * 
	 * @return the method
	 * @author Picono435
	 *
	 */
	public double getMethod() {
		return method;
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
	}
	
	/**
	 * Get the current method level (amount of works done) from a player
	 * 
	 * @return the method
	 * @author Picono435
	 *
	 */
	public double getMethodLevel() {
		return level;
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
	}
	
	/**
	 * Sets the job of a player
	 * 
	 * @param job The Job
	 * @author Picono435
	 *
	 */
	public void setJob(Job job) {
		this.job = job;
	}
	
	/**
	 * Check if a player has accepted a work or not
	 * 
	 * @return true if it is working, false if not
	 * @author Picono435
	 *
	 */
	public boolean isWorking() {
		return isWorking;
	}
	
	/**
	 * Set if the player is working or not
	 * 
	 * @param isWorking if it's working or not
	 * @author Picono435
	 *
	 */
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	
	/**
	 * Gets the current salary of a player
	 * 
	 * @return the salary
	 * @author Picono435
	 *
	 */
	public double getSalary() {
		return salary;
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
				
		int reqmethod = (int) (job.getMethod() * level * PicoJobsAPI.getSettingsManager().getKillsFrequency());
		
		if(getMethod() >= reqmethod) {
			double salary = job.getSalary() * level * PicoJobsAPI.getSettingsManager().getSalaryFrequency();
			setMethodLevel(level + 1);
			setMethod(0);
			setWorking(false);
			setSalary(getSalary() + salary);
			return true;
		}
		return false;
	}
}
