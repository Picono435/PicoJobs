package com.gmail.picono435.picojobs.nukkit.hooks.workzones;

import cn.nukkit.level.Level;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Level> requiredField;

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public void onRegister() {
        this.requiredField = new RequiredField<>("worlds", new RequiredFieldType<String, Level>(String.class, Level.class) {
            @Override
            public Level toValue(@Nonnull String primitive) {
                return PicoJobsNukkit.getInstance().getServer().getLevelByName(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(Level value) {
                return value.getName();
            }

            @Nonnull
            @Override
            public List<Level> getSuggestions() {
                return new ArrayList<>(PicoJobsNukkit.getInstance().getServer().getLevels().values());
            }
        }, true);
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Level> regions = this.requiredField.getValueList(jp.getJob());
        return regions.contains(PicoJobsNukkit.getInstance().getServer().getPlayer(player).get().getLevel());
    }

    @Override
    public RequiredField<String, Level> getRequiredField() {
        return requiredField;
    }
}
