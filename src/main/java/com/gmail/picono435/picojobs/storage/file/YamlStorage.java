package com.gmail.picono435.picojobs.storage.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

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
