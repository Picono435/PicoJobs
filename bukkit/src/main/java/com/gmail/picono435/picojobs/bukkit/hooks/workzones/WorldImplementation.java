package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String, World> requiredField;

    public WorldImplementation() {
        this.requiredField = new RequiredField<>("worlds", new RequiredFieldType<String, World>(String.class, World.class) {
            @Override
            public World toValue(@Nonnull String primitive) {
                return Bukkit.getWorld(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(World value) {
                return value.getName();
            }

            @Nonnull
            @Override
            public List<World> getSuggestions() {
                return Bukkit.getWorlds();
            }
        }, true);
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<World> regions = this.requiredField.getValueList(jp.getJob());
        return regions.contains(Bukkit.getPlayer(player).getWorld());
    }

    @Override
    public RequiredField<String, World> getRequiredField() {
        return requiredField;
    }
}
