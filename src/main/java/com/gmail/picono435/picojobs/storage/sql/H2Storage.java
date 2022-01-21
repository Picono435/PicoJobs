package com.gmail.picono435.picojobs.storage.sql;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.RunScript;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class H2Storage extends HikariStorageFactory {

    @Override
    protected boolean initializeStorage() throws Exception {
        configurationSection = PicoJobsAPI.getSettingsManager().getRemoteSqlConfiguration();

        if(PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2.zip").toFile().exists()) {
            RunScript.main("-url jdbc:h2:$f -script $f.zip -options compression zip variable_binary".replace("$f", PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2").toAbsolutePath().toString()).split(" "));
        }

        config.setDriverClassName("org.h2.Driver");
        //config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setJdbcUrl("jdbc:h2:" + PicoJobsPlugin.getInstance().getDataFolder().toPath().toAbsolutePath().resolve("storage").resolve("picojobs-h2"));

        this.hikari = new HikariDataSource(config);

        try(Connection conn = hikari.getConnection();
            PreparedStatement stm = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + configurationSection.getString("tablename") + " (`uuid` VARCHAR(255) NOT NULL, `job` TEXT DEFAULT NULL, `method` DOUBLE DEFAULT '0', `level` DOUBLE DEFAULT '0', `salary` DOUBLE DEFAULT '0', `is-working` BOOLEAN DEFAULT FALSE, PRIMARY KEY (`uuid`));")) {
            stm.execute();
        }
        return false;
    }

    @Override
    public void destroyStorage() {
        hikari.close();
    }
}
