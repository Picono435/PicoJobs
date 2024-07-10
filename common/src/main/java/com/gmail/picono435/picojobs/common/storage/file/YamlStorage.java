package com.gmail.picono435.picojobs.common.storage.file;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class YamlStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return YamlConfigurationLoader.builder()
        .indent(2)
        .path(PicoJobsCommon.getConfigDir().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.yml"))
        .build();
	}
}
