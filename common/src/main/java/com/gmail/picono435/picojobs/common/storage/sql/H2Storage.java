package com.gmail.picono435.picojobs.common.storage.sql;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.RunScript;

public class H2Storage extends HikariStorageFactory {

    @Override
    protected boolean initializeStorage() throws Exception {
        configurationNode = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();

        if(PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.zip").toFile().exists()) {
            PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.mv.db").toFile().delete();
            RunScript.main("-url jdbc:h2:$f -script $f.zip -options compression zip".replace("$f", PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString()).split(" "));
            PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.zip").toFile().delete();
        }

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:" + PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"));

        this.hikari = new HikariDataSource(config);

        this.createTable();
        return false;
    }

    @Override
    public void destroyStorage() {
        hikari.close();
    }
}
