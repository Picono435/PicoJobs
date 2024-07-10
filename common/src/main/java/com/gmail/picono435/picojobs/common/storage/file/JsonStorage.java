package com.gmail.picono435.picojobs.common.storage.file;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

public class JsonStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return GsonConfigurationLoader.builder()
        .indent(2)
        .path(PicoJobsCommon.getConfigDir().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.json"))
        .build();
	}

}
