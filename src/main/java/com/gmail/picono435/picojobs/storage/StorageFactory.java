package com.gmail.picono435.picojobs.storage;

import java.util.UUID;

public abstract class StorageFactory {
	
	protected abstract boolean initializeStorage() throws Exception;

	public abstract boolean createPlayer(UUID uuid) throws Exception;
	public abstract boolean playerExists(UUID uuid) throws Exception;
	
	public abstract String getJob(UUID uuid) throws Exception;
	public abstract double getMethod(UUID uuid) throws Exception;
	public abstract double getMethodLevel(UUID uuid) throws Exception;
	public abstract boolean isWorking(UUID uuid) throws Exception;
	public abstract double getSalary(UUID uuid) throws Exception;
	public abstract long getSalaryCooldown(UUID uuid) throws Exception;
	public abstract long getLeaveCooldown(UUID uuid) throws Exception;
	
	public abstract boolean setJob(UUID uuid, String job) throws Exception;
	public abstract boolean setMethod(UUID uuid, double method) throws Exception;
	public abstract boolean setMethodLevel(UUID uuid, double level) throws Exception;
	public abstract boolean setWorking(UUID uuid, boolean isWorking) throws Exception;
	public abstract boolean setSalary(UUID uuid, double salary) throws Exception;
	public abstract boolean setSalaryCooldown(UUID uuid, long salaryCooldown) throws Exception;
	public abstract boolean setLeaveCooldown(UUID uuid, long leaveCooldown) throws Exception;
	
	protected abstract void destroyStorage();
}
