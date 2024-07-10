package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GriefPreventionImplementation extends WorkZoneImplementation {

    protected RequiredField<Long, Claim> requiredField;

    public GriefPreventionImplementation() {
        this.requiredPlugin = "GriefPrevention";
    }

    @Override
    public String getName() {
        return "GRIEFPREVENTION";
    }

    @Override
    public void onRegister() {
        this.requiredField = new RequiredField<>("claims", new RequiredFieldType<Long, Claim>(Long.class, Claim.class) {
            @Override
            public Claim toValue(@Nonnull Long primitive) {
                return GriefPrevention.instance.dataStore.getClaim(primitive);
            }

            @Nonnull
            @Override
            public Long toPrimitive(Claim value) {
                return value.getID();
            }

            @Nonnull
            @Override
            public List<Claim> getSuggestions() {
                return new ArrayList<>(GriefPrevention.instance.dataStore.getClaims());
            }
        }, true);
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        Location location = onlinePlayer.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Claim> regions = this.requiredField.getValueList(jp.getJob());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        return regions.contains(claim);
    }

    @Override
    public RequiredField<Long, Claim> getRequiredField() {
        return requiredField;
    }
}