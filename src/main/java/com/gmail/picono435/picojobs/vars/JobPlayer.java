package com.gmail.picono435.picojobs.vars;

public class JobPlayer {
	
	private Job job;
	private double method;
	private boolean isWorking;
	private double salary;
	
	public JobPlayer(Job job, double method, double salary, boolean isWorking) {
		this.job = job;
		this.method = method;
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
