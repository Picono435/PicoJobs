package com.gmail.picono435.picojobs.storage.sql;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

public class H2Storage extends HikariStorageFactory {

    @Override
    protected boolean initializeStorage() throws Exception {
        configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();

        config.setDriverClassName("org.h2.Driver");
        //config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setJdbcUrl("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"));

        this.hikari = new HikariDataSource(config);

        try(Connection conn = hikari.getConnection()) {
            PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + configurationSection.getString("tablename") + " (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));");
            stm.execute();
            stm.close();
            Path scriptFile = PicoJobsPlugin.getInstance().getDataFolder().toPath().resolve("storage").resolve("script").toAbsolutePath();
            if(scriptFile.toFile().exists()) {
                // Import data to the database
                PreparedStatement stm2 = conn.prepareStatement("RUNSCRIPT FROM ?");
                stm2.setString(1, scriptFile.toString());
                stm2.execute();
                stm2.close();
                scriptFile.toFile().delete();
                conn.close();
            }
            conn.close();
        }
        return false;
    }

    @Override
    public void destroyStorage() {
        hikari.close();
    }

    public void backupDataTo(File file) throws Exception {
        try(Connection conn = hikari.getConnection()) {
            PreparedStatement stm = conn.prepareStatement("SCRIPT TO ?");
            stm.setString(1, file.getAbsolutePath());
            stm.execute();
            stm.close();
        }
    }

    public void retrieveDataFrom(File file) throws Exception {
        try(Connection conn = hikari.getConnection()) {
            PreparedStatement stm = conn.prepareStatement("RUNSCRIPT FROM ?");
            stm.setString(1, file.getAbsolutePath());
            stm.execute();
            stm.close();
        }
    }
}
