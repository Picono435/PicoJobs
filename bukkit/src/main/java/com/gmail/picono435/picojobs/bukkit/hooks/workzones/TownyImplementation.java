package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class TownyImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Town> requiredField;

    public TownyImplementation() {
        this.requiredPlugin = "Towny";
    }

    @Override
    public String getName() {
        return "TOWNY";
    }

    @Override
    public void onRegister() {
        this.requiredField = new RequiredField<>("towns", new RequiredFieldType<String, Town>(String.class, Town.class) {
            @Override
            public Town toValue(@Nonnull String primitive) {
                return TownyAPI.getInstance().getTown(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(Town value) {
                return value.getName();
            }

            @Nonnull
            @Override
            public List<Town> getSuggestions() {
                return TownyAPI.getInstance().getTowns();
            }
        }, true);
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Town> regions = this.requiredField.getValueList(jp.getJob());
        return regions.contains(TownyAPI.getInstance().getTown(onlinePlayer.getLocation()));
    }
}
