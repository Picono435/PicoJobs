package com.gmail.picono435.picojobs.common.storage.file;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

public class HoconStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return HoconConfigurationLoader.builder()
        .path(PicoJobsCommon.getConfigDir().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.conf"))
        .build();
	}

}
