package com.gmail.picono435.picojobs.storage.file;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.json.JSONConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class JsonStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return JSONConfigurationLoader.builder()
        .setIndent(2)
        .setPath(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.json"))
        .build();
	}

}
