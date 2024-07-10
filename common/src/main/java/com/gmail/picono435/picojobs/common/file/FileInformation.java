package com.gmail.picono435.picojobs.common.file;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class FileInformation {
    private YamlConfigurationLoader loader;
    private CommentedConfigurationNode rootNode;

    public FileInformation(YamlConfigurationLoader loader, CommentedConfigurationNode rootNode) {
        this.loader = loader;
        this.rootNode = rootNode;
    }

    public YamlConfigurationLoader getLoader() {
        return loader;
    }

    public CommentedConfigurationNode getRootNode() {
        return rootNode;
    }
}
