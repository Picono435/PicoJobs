package com.gmail.picono435.picojobs.sponge.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.UUID;

public class GriefDefenderImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public GriefDefenderImplementation() {
        this.requiredPlugin = "GriefDefender";
        this.requiredField = new RequiredField<>("claims");
    }

    @Override
    public String getName() {
        return "GRIEFDEFENDER";
    }

    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Sponge.server().player(player).get();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        Claim claim = GriefDefender.getCore().getClaimAt(onlinePlayer.location());
        if(claim == null || claim.isWilderness()) return regions.contains("wilderness");
        return regions.contains(claim.getUniqueId().toString());
    }
}