package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class GriefDefenderImplementation extends WorkZoneImplementation {

    public GriefDefenderImplementation() {
        this.requiredPlugin = "GriefDefender";
        this.requiredField = new RequiredField("claims", RequiredField.RequiredFieldType.STRING_LIST);
    }

    @Override
    public String getName() {
        return "GRIEFDEFENDER";
    }

    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        Location location = onlinePlayer.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = null;
        try {
            regions = FileManager.getJobsNode().node("jobs", jp.getJob().getID(), requiredField.getName()).getList(String.class);
        } catch (SerializationException event) {
            throw new RuntimeException(event);
        }
        Claim claim = GriefDefender.getCore().getClaimAt(onlinePlayer.getLocation());
        if(claim == null || claim.isWilderness()) return regions.contains("wilderness");
        return regions.contains(claim.getUniqueId().toString());
    }
}