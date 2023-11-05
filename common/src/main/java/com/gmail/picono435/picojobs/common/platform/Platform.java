package com.gmail.picono435.picojobs.common.platform;

public enum Platform {
    BUKKIT("VAULT"),
    FORGE("GRAND_ECONOMY"),
    FABRIC("GRAND_ECONOMY"),
    QUILT("GRAND_ECONOMY"),
    NUKKIT("ECONOMYAPI"),
    SPONGE("SPONGE");

    private String defaultEconomy;

    Platform(String defaultEconomy) {
        this.defaultEconomy = defaultEconomy;
    }

    public String getDefaultEconomy() {
        return defaultEconomy;
    }
}
