package com.gmail.picono435.picojobs.common.platform;

public enum Platform {
    BUKKIT("VAULT", true),
    FORGE("GRAND_ECONOMY", false),
    FABRIC("GRAND_ECONOMY", false),
    NUKKIT("ECONOMYAPI", true),
    SPONGE("SPONGE", true);

    private final String defaultEconomy;
    private final boolean slimDependencies;

    Platform(String defaultEconomy, boolean slimDependencies) {
        this.defaultEconomy = defaultEconomy;
        this.slimDependencies = slimDependencies;
    }

    public String getDefaultEconomy() {
        return defaultEconomy;
    }

    public boolean isSlimDependencies() {
        return slimDependencies;
    }
}
