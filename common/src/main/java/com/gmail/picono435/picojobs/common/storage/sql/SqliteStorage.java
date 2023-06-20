package com.gmail.picono435.picojobs.common.storage.sql;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

public class SqliteStorage extends HikariStorageFactory {

    @Override
    protected boolean initializeStorage() throws Exception {
        configurationNode = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();

        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + PicoJobsCommon.getConfigDir().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-sqlite.db"));

        this.hikari = new HikariDataSource(config);

        this.createTable();
        return false;
    }

    @Override
    public void destroyStorage() {
        hikari.close();
    }
}
