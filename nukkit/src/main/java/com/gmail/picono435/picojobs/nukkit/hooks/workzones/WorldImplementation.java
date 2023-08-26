package com.gmail.picono435.picojobs.nukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

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
        return regions.contains(PicoJobsNukkit.getInstance().getServer().getPlayer(player).get().getLevel().getName());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
