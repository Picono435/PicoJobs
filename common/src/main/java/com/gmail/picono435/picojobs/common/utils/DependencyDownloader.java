package com.gmail.picono435.picojobs.common.utils;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DependencyDownloader {
    
    private static final List<Dependency> DEPENDENCIES = new ArrayList<>();
    private static final String MAVEN_CENTRAL = "https://repo1.maven.org/maven2/";
    
    static {
        DEPENDENCIES.add(new Dependency("org.mongodb", "mongodb-driver-sync", "4.9.1"));
        DEPENDENCIES.add(new Dependency("com.zaxxer", "HikariCP", "4.0.3"));
        DEPENDENCIES.add(new Dependency("com.h2database", "h2", "2.1.214"));
        DEPENDENCIES.add(new Dependency("org.xerial", "sqlite-jdbc", "3.46.0.0"));
        DEPENDENCIES.add(new Dependency("org.mariadb.jdbc", "mariadb-java-client", "3.4.0"));
        DEPENDENCIES.add(new Dependency("com.mysql", "mysql-connector-j", "8.4.0"));
    }
    
    public static void downloadAndLoadDependencies() {
        PicoJobsCommon.getLogger().info("Loading dependencies, this might take some time on first startup...");
        
        Path libsDir = PicoJobsCommon.getConfigDir().toPath().resolve("libraries");
        try {
            Files.createDirectories(libsDir);
        } catch (IOException e) {
            PicoJobsCommon.getLogger().error("Failed to create libraries directory", e);
            return;
        }
        
        List<URL> jarUrls = new ArrayList<>();
        
        for (Dependency dep : DEPENDENCIES) {
            try {
                Path jarFile = downloadDependency(dep, libsDir);
                if (jarFile != null) {
                    jarUrls.add(jarFile.toUri().toURL());
                }
            } catch (Exception e) {
                PicoJobsCommon.getLogger().error("Failed to download dependency: " + dep.getArtifactId(), e);
            }
        }
        
        loadJarsIntoClasspath(jarUrls);
        
        PicoJobsCommon.getLogger().info("All dependencies loaded successfully.");
    }
    
    private static Path downloadDependency(Dependency dep, Path libsDir) throws IOException {
        String fileName = dep.getArtifactId() + "-" + dep.getVersion() + ".jar";
        Path jarFile = libsDir.resolve(fileName);
        
        if (Files.exists(jarFile)) {
            PicoJobsCommon.getLogger().debug("Dependency already exists: " + fileName);
            return jarFile;
        }
        
        String url = MAVEN_CENTRAL + dep.getGroupId().replace('.', '/') + "/" + 
                     dep.getArtifactId() + "/" + dep.getVersion() + "/" + fileName;
        
        PicoJobsCommon.getLogger().info("Downloading: " + fileName);
        
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "PicoJobs-DependencyDownloader/1.0");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        
        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to download " + fileName + ": HTTP " + connection.getResponseCode());
        }
        
        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(jarFile.toFile())) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        
        PicoJobsCommon.getLogger().info("Downloaded: " + fileName);
        return jarFile;
    }
    
    private static void loadJarsIntoClasspath(List<URL> jarUrls) {
        if (jarUrls.isEmpty()) {
            return;
        }
        
        try {
            ClassLoader pluginClassLoader = PicoJobsCommon.class.getClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(
                jarUrls.toArray(new URL[0]), 
                pluginClassLoader
            );
            
            Thread.currentThread().setContextClassLoader(urlClassLoader);
            
            PicoJobsCommon.getLogger().info("Loaded " + jarUrls.size() + " storage dependency JARs");
            
        } catch (Exception e) {
            PicoJobsCommon.getLogger().error("Failed to load JARs into classpath", e);
        }
    }
    
    private static class Dependency {
        private final String groupId;
        private final String artifactId;
        private final String version;
        
        public Dependency(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }
        
        public String getGroupId() {
            return groupId;
        }
        
        public String getArtifactId() {
            return artifactId;
        }
        
        public String getVersion() {
            return version;
        }
    }
}