package com.gmail.picono435.picojobs.common.storage;

import java.util.Locale;

public enum StorageType {
    MYSQL(),
    MARIADB(),
    POSTGRE(),
    MONGODB(),
    H2(),
    SQLITE(),
    YAML(),
    JSON(),
    HOCON();

    public static StorageType fromString(String name) {
        return StorageType.valueOf(name.toUpperCase(Locale.ROOT));
    }
}
