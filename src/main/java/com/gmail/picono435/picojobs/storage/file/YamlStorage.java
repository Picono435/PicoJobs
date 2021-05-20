package com.gmail.picono435.picojobs.storage.file;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public class YamlStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return YAMLConfigurationLoader.builder()
        .setIndent(2)
        .setPath(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.yml"))
        .build();
	}

}
