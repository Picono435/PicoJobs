package com.gmail.picono435.picojobs.storage.file;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class HoconStorage extends ConfigurationStorageFactory {

	@Override
	protected ConfigurationLoader<? extends ConfigurationNode> loadFile() {
		return HoconConfigurationLoader.builder()
        .path(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.conf"))
        .build();
	}

}
