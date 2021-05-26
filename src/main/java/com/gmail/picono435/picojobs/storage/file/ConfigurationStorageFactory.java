package com.gmail.picono435.picojobs.storage.file;

import java.util.UUID;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import com.gmail.picono435.picojobs.storage.StorageFactory;

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
		this.root.node(uuid.toString(), "method").set(0D);
		this.root.node(uuid.toString(), "level").set(0D);
		this.root.node(uuid.toString(), "salary").set(0D);
		this.root.node(uuid.toString(), "is-working").set(false);
		this.loader.save(root);
		return true;
	}

	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		return !this.root.node(uuid.toString()).virtual();
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		return this.root.node(uuid.toString(), "job").getString();
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		return this.root.node(uuid.toString(), "method").getDouble();
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		return this.root.node(uuid.toString(), "level").getDouble();
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		return this.root.node(uuid.toString(), "is-working").getBoolean();
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		return this.root.node(uuid.toString(), "salary").getDouble();
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		this.root.node(uuid.toString(), "job").set(job);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		this.root.node(uuid.toString(), "method").set(method);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		this.root.node(uuid.toString(), "level").set(level);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		this.root.node(uuid.toString(), "is-working").set(isWorking);
		this.loader.save(this.root);
		return true;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		this.root.node(uuid.toString(), "salary").set(salary);
		this.loader.save(this.root);
		return true;
	}
	
	@Override
	protected void destroyStorage() {}

}
