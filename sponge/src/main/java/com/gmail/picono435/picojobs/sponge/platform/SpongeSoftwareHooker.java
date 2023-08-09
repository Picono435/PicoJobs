package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.SoftwareHooker;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import com.gmail.picono435.picojobs.sponge.listeners.SpongeJoinCacheListener;
import org.spongepowered.api.Sponge;

public class SpongeSoftwareHooker implements SoftwareHooker {
    @Override
    public void hookInPhase(Phase phase) {
        switch (phase) {
            case ONE:
                break;
            case TWO:
                Sponge.eventManager().registerListeners(PicoJobsSponge.getInstance().getPluginContainer(), new SpongeJoinCacheListener());
                break;
            case THREE:
                break;
        }
    }
}
