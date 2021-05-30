package com.gmail.picono435.picojobs.dependencies;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public enum Dependency {
	/* Required Libraries */
	SLF4J_SIMPLE(
            "org.slf4j",
            "slf4j-simple",
            "1.7.30"
    ),
    SLF4J_API(
            "org.slf4j",
            "slf4j-api",
            "1.7.30"
    ),
    FASTERXML_JACKSON_DATABIND(
    		"com.fasterxml.jackson.core",
    		"jackson-databind",
    		"2.12.3"
    ),
	FASTERXML_JACKSON_YAML(
    		"com.fasterxml.jackson.dataformat",
    		"jackson-dataformat-yaml",
    		"2.12.3"
    ),
	FANCIFUL(
    		"com.github.mkremins",
    		"fanciful",
    		"18dc039f5b",
    		"https://jitpack.io"
    ),
	/* Storage Dependencies */
	MONGODB_DRIVER(
    		"org.mongodb",
    		"mongodb-driver-sync",
    		"4.2.3"
    ),
	HIKARICP(
    		"com.zaxxer",
    		"HikariCP",
    		"4.0.3"
    ),
	H2_DATABASE(
    		"com.h2database",
    		"h2",
    		"1.4.199"
    ),
	SQLITE_JBDC(
    		"org.xerial",
    		"sqlite-jdbc",
    		"3.34.0"
    ),
	MARIADB_JBDC(
    		"org.mariadb.jdbc",
    		"mariadb-java-client",
    		"2.7.3"
    ),
	MYSQL_JBDC(
    		"mysql",
    		"mysql-connector-java",
    		"8.0.25"
    ),
	GEANTYREF("io.leangen.geantyref",
			"geantyref",
			"1.3.4"
	),
	CHECKERFRAMEWORK("org.checkerframework",
			"checker-qual",
			"3.12.0"
	),
	CONFIGURATE_CORE(
    		"org.spongepowered",
    		"configurate-core",
    		"4.1.1",
    		Dependency.GEANTYREF,
    		Dependency.CHECKERFRAMEWORK
    ),
	CONFIGURATE_GSON(
    		"org.spongepowered",
    		"configurate-gson",
    		"4.1.1"
    ),
	CONFIGURATE_YAML(
    		"org.spongepowered",
    		"configurate-yaml",
    		"4.1.1"
    ),
	CONFIGURATE_HOCON(
    		"org.spongepowered",
    		"configurate-hocon",
    		"4.1.1"
    );
	
	private final String groupId;
    private final String artifactId;
    private final String version;
    private final String repoUrl;
    private final Dependency[] dependencies;

    Dependency(String groupId, String artifactId, String version, Dependency... dependencies) {
        this.groupId = Objects.requireNonNull(groupId, "groupId");
        this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
        this.version = Objects.requireNonNull(version, "version");
        // Please ask @lucko before using this mirror in your own project.
        this.repoUrl = "https://repo1.maven.org/maven2/";
        this.dependencies = dependencies;
    }
    
    Dependency(String groupId, String artifactId, String version, String repoUrl, Dependency... dependencies) {
        this.groupId = Objects.requireNonNull(groupId, "groupId");
        this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
        this.version = Objects.requireNonNull(version, "version");
        this.repoUrl = Objects.requireNonNull(repoUrl, "repoUrl");
        this.dependencies = dependencies;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public String getRepoUrl() {
        return this.repoUrl;
    }
    
    public Dependency[] getDependencies() {
    	return this.dependencies;
    }

    public URL getUrl() throws MalformedURLException {
        String repo = this.repoUrl;
        if (!repo.endsWith("/")) {
            repo += "/";
        }
        repo += "%s/%s/%s/%s-%s.jar";

        String url = String.format(repo, this.groupId.replace(".", "/"), this.artifactId, this.version, this.artifactId, this.version);
        return new URL(url);
    }
    
    public static Dependency[] getRequiredDependencies() {
    	Dependency[] dps = {SLF4J_SIMPLE, SLF4J_API, FASTERXML_JACKSON_DATABIND,
    			FASTERXML_JACKSON_YAML, FANCIFUL};
    	return dps;
    }

    @Override
    public String toString() {
        return "Dependency(" +
                "groupId=" + this.getGroupId() + ", " +
                "artifactId=" + this.getArtifactId() + ", " +
                "version=" + this.getVersion() + ", " +
                "repoUrl=" + this.getRepoUrl() + ")";
    }
}
