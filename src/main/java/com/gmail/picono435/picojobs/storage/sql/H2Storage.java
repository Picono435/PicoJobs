package com.gmail.picono435.picojobs.storage.sql;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.RunScript;

public class H2Storage extends HikariStorageFactory {

    @Override
    protected boolean initializeStorage() throws Exception {
        configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();

        if(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.zip").toFile().exists()) {
            PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.mv.db").toFile().delete();
            RunScript.main("-url jdbc:h2:$f -script $f.zip -options compression zip".replace("$f", PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString()).split(" "));
            PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.zip").toFile().delete();
        }

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"));

        this.hikari = new HikariDataSource(config);

        this.createTable();
        return false;
    }

    @Override
    public void destroyStorage() {
        hikari.close();
    }
}
