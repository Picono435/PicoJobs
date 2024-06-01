package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class GriefDefenderImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Claim> requiredField;

    public GriefDefenderImplementation() {
        this.requiredPlugin = "GriefDefender";
    }

    @Override
    public String getName() {
        return "GRIEFDEFENDER";
    }

    @Override
    public void onRegister() {
        this.requiredField = new RequiredField<>("claims", new RequiredFieldType<String, Claim>(String.class, Claim.class) {
            @Nullable
            @Override
            public Claim toValue(@Nonnull String primitive) {
                if(primitive.equalsIgnoreCase("wilderness")) return null;
                return GriefDefender.getCore().getClaim(UUID.fromString(primitive));
            }

            @Nonnull
            @Override
            public String toPrimitive(@Nullable Claim value) {
                if(value == null) return "wilderness";
                return value.getUniqueId().toString();
            }

            @Nonnull
            @Override
            public List<Claim> getSuggestions() {
                List<Claim> suggestions = GriefDefender.getCore().getAllClaims();
                suggestions.add(null);
                return suggestions;
            }
        }, true, null);
    }

    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Claim> regions = this.requiredField.getValueList(jp.getJob());
        Claim claim = GriefDefender.getCore().getClaimAt(onlinePlayer.getLocation());
        if(claim == null || claim.isWilderness()) return regions.contains(null);
        return regions.contains(claim);
    }

    @Override
    public RequiredField<String, Claim> getRequiredField() {
        return requiredField;
    }
}