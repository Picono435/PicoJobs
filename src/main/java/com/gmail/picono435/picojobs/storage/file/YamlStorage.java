package com.gmail.picono435.picojobs.storage.file;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class YamlStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return YamlConfigurationLoader.builder()
        .indent(2)
        .path(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.yml"))
        .build();
	}

}
