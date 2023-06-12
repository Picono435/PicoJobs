package com.gmail.picono435.picojobs.common.file;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.apache.commons.io.FileUtils;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileManager {

    private static YamlConfigurationLoader configLoader;
    private static CommentedConfigurationNode configNode;

    private static YamlConfigurationLoader guiLoader;
    private static CommentedConfigurationNode guiNode;

    private static YamlConfigurationLoader jobsLoader;
    private static CommentedConfigurationNode jobsNode;
    private static YamlConfigurationLoader languageLoader;
    private static CommentedConfigurationNode languageNode;

    public boolean init() {
        createConfigFile();
        createGUIFile();
        createJobsFile();
        createLanguageFile(PicoJobsAPI.getSettingsManager().getLanguage());
        return true;
    }

    private FileInformation createFile(String filePath) {
        try {
            File file = new File(PicoJobsCommon.getConfigDir(), filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                URL inputUrl = getClass().getResource(filePath);
                FileUtils.copyURLToFile(inputUrl, file);
            }

            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .path(file.toPath())
                    .build();

            CommentedConfigurationNode root = loader.load();
            return new FileInformation(loader, root);
        } catch (IOException e) {
            System.err.println("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return null;
        }
    }

    private void createConfigFile() {
        FileInformation fileInformation = createFile("config.yml");
        configLoader = fileInformation.getLoader();
        configNode = fileInformation.getRootNode();
    }

    private void createGUIFile() {
        FileInformation fileInformation = createFile("settings" + File.separator + "guis.yml");
        guiLoader = fileInformation.getLoader();
        guiNode = fileInformation.getRootNode();
    }

    private void createJobsFile() {
        FileInformation fileInformation = createFile("settings" + File.separator + "jobs.yml");
        jobsLoader = fileInformation.getLoader();
        jobsNode = fileInformation.getRootNode();
    }

    private void createLanguageFile(String language) {
        FileInformation fileInformation = createFile("langs" + File.separator + language + ".yml");
        languageLoader = fileInformation.getLoader();
        languageNode = fileInformation.getRootNode();
    }

    // TODO: Add migrations (Old ones are probably no longer needed)
    public void migrateFiles() {
        try {
            URL defaultsInJarURL = this.getClass().getResource("config.yml");
            YamlConfigurationLoader defaultsLoader = YamlConfigurationLoader.builder().url(defaultsInJarURL).build();
            ConfigurationNode defaults = defaultsLoader.load();

            configNode.mergeFrom(defaults);
            configLoader.save(configNode);
        } catch (IOException e) {
            System.err.println("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    public static CommentedConfigurationNode getConfigNode() {
        return configNode;
    }

    public static CommentedConfigurationNode getGuiNode() {
        return guiNode;
    }

    public static CommentedConfigurationNode getJobsNode() {
        return jobsNode;
    }

    public static CommentedConfigurationNode getLanguageNode() {
        return languageNode;
    }
}
