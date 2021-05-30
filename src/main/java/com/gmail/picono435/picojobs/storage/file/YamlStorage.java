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
		File oldData = new File(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("data.yml").toString());
		if(oldData.exists()) {
			try {
				String content = new String(Files.readAllBytes(oldData.toPath()));
				content = content.replace("playerdata:", "");
				content = content.replace("/^  /mg", "");
				oldData.delete();
				File newData = new File(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
						.resolve("storage").resolve("picojobs.yml").toString());
				newData.getParentFile().mkdirs();
				newData.createNewFile();
				FileWriter myWriter = new FileWriter(newData);
				myWriter.write(content);
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return YamlConfigurationLoader.builder()
        .indent(2)
        .path(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath()
        		.resolve("storage").resolve("picojobs.yml"))
        .build();
	}

}
