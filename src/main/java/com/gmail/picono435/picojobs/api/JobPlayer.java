package com.gmail.picono435.picojobs.api;

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
	
	public boolean hasJob() {
		return job != null;
	}
	
	public Job getJob() {
		return job;
	}
	
	public double getMethod() {
		return method;
	}
	
	public void setMethod(double method) {
		this.method = method;
	}
	
	public double getMethodLevel() {
		return level;
	}
	
	public void setMethodLevel(double level) {
		this.level = level;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public boolean isWorking() {
		return isWorking;
	}
	
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void addSalary(double salary) {
		setSalary(getSalary() + salary);
	}
	
	public void removeSalary(double salary) {
		setSalary(getSalary() - salary);
	}
}
