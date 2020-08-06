package com.gmail.picono435.picojobs.vars;

public class JobPlayer {
	
	private Job job;
	private double reqmethod;
	
	public JobPlayer(Job job, double reqmethod) {
		this.job = job;
		this.reqmethod = reqmethod;
	}
	
	public boolean hasJob() {
		return job != null;
	}
	
	public Job getJob() {
		return job;
	}
	
	public double getRequiredMethod() {
		return reqmethod;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}

}
