package com.gmail.picono435.picojobs.sponge.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import org.spongepowered.api.Sponge;

import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public WorldImplementation() {
        this.requiredField = new RequiredField<>("worlds");
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        return regions.contains(Sponge.server().player(player).get().serverLocation().world().properties().name());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
