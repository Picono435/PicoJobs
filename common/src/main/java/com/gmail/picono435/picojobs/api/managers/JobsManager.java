package com.gmail.picono435.picojobs.api.managers;

import java.util.Collection;

import com.gmail.picono435.picojobs.api.Job;

public class JobsManager {
	
	@SuppressWarnings("unused")
	/**
	 * Create the manager of jobs, with it you can change everything of jobs.
	 * 
	 * @author Picono435
	 */
	public JobsManager() {
	}
	
	/**
	 * Gets a job with jobname
	 * 
	 * @param jobname - the job name
	 * @return the job
	 * @author Picono435
	 */
	public Job getJob(String jobname) {
		if(!PicoJobsPlugin.getInstance().jobs.containsKey(jobname)) {
			return null;
		}
		return PicoJobsPlugin.getInstance().jobs.get(jobname);
	}
	
	/**
	 * Gets a job with job displayname
	 * 
	 * @param displayname - the job display name
	 * @return the job
	 * @author Picono435
	 */
	public Job getJobByDisplayname(String displayname) {
		for(Job job : getJobs()) {
			if(job.getDisplayName().equals(displayname)) {
				return job;
			}
		}
		return null;
	}
	
	/**
	 * Gets a job with stripped color job displayname
	 * 
	 * @param displayname - the job display name
	 * @return the job
	 * @author Picono435
	 */
	public Job getJobByStrippedColorDisplayname(String displayname) {
		for(Job job : getJobs()) {
			if(ChatColor.stripColor(job.getDisplayName()).equals(displayname)) {
				return job;
			}
		}
		return null;
	}
	
	/**
	 * Gets all the jobs
	 * 
	 * @return all the jobs
	 * @author Picono435
	 */
	public Collection<Job> getJobs() {
		return PicoJobsPlugin.getInstance().jobs.values();
	}
}
