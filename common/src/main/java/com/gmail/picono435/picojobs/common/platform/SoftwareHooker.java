package com.gmail.picono435.picojobs.common.platform;

public interface SoftwareHooker {

    void hookInPhase(Phase phase);

    enum Phase {
        ONE(),
        TWO(),
        THREE();
    }
}