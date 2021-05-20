package com.gmail.picono435.picojobs.storage.file;

import java.util.UUID;

import com.gmail.picono435.picojobs.storage.StorageFactory;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public abstract class ConfigurationStorageFactory extends StorageFactory {

	private ConfigurationLoader<? extends ConfigurationNode> loader;
	private ConfigurationNode root;
	
	protected abstract ConfigurationLoader<? extends ConfigurationNode> loadFile();
	
	@Override
	protected boolean initializeStorage() throws Exception {
		this.loader = loadFile();
		this.root = this.loader.load();
		return true;
	}
	
	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		this.root.getNode(uuid, "method").setValue(0D);
		this.root.getNode(uuid, "level").setValue(0D);
		this.root.getNode(uuid, "salary").setValue(0D);
		this.root.getNode(uuid, "is-working").setValue(false);
		this.loader.save(root);
		return true;
	}

	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		return !this.root.getNode(uuid).isVirtual();
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		return this.root.getNode(uuid, "job").getString();
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		return this.root.getNode(uuid, "method").getDouble();
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		return this.root.getNode(uuid, "level").getDouble();
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		return this.root.getNode(uuid, "is-working").getBoolean();
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		return this.root.getNode(uuid, "salary").getDouble();
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		this.root.getNode(uuid, "job").setValue(job);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		this.root.getNode(uuid, "method").setValue(method);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		this.root.getNode(uuid, "level").setValue(level);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		this.root.getNode(uuid, "is-working").setValue(isWorking);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		this.root.getNode(uuid, "salary").setValue(salary);
		this.loader.save(this.root);
		return true;
	}
	
	@Override
	protected void destroyStorage() {}

}
