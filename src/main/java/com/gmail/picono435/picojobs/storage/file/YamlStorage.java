package com.gmail.picono435.picojobs.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.storage.StorageFactory;

public class YamlStorage extends StorageFactory {

	private FileConfiguration data;
	private File dataFile;
	
	@Override
	protected boolean initializeStorage() throws Exception {
		dataFile = new File(PicoJobsPlugin.getInstance().getDataFolder(), "picojobs-yaml.yml");
        if (!dataFile.exists()) {
        	dataFile.getParentFile().mkdirs();
        	PicoJobsPlugin.getInstance().saveResource("picojobs-yaml.yml", false);
         }

        data = new YamlConfiguration();
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		ConfigurationSection player = data.getConfigurationSection("jobplayers").createSection(uuid.toString());
		player.set("job", null);
		player.set("method", 0D);
		player.set("level", 0D);
		player.set("salary", 0D);
		player.set("is-working", false);
		data.save(dataFile);
		return true;
	}

	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		return data.contains("jobplayers." + uuid.toString());
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		return data.getString("jobplayers." + uuid + ".job");
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		return data.getDouble("jobplayers." + uuid + ".method");
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		return data.getDouble("jobplayers." + uuid + ".level");
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		return data.getBoolean("jobplayers." + uuid + ".is-working");
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		return data.getDouble("jobplayers." + uuid + ".salary");
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		data.set("jobplayers." + uuid + ".job", job);
		data.save(dataFile);
		return true;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		data.set("jobplayers." + uuid + ".method", method);
		data.save(dataFile);
		return true;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		data.set("jobplayers." + uuid + ".level", level);
		data.save(dataFile);
		return true;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		data.set("jobplayers." + uuid + ".is-working", isWorking);
		data.save(dataFile);
		return true;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		data.set("jobplayers." + uuid + ".salary", salary);
		data.save(dataFile);
		return true;
	}

	@Override
	protected void destroyStorage() {}

}
