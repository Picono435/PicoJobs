package com.gmail.picono435.picojobs.storage.file;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class JsonStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return GsonConfigurationLoader.builder()
        .indent(2)
        .path(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.json"))
        .build();
	}

}
