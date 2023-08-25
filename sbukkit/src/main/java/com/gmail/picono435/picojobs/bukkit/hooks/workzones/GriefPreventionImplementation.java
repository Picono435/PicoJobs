package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class GriefPreventionImplementation extends WorkZoneImplementation {

    protected RequiredField<Long> requiredField;

    public GriefPreventionImplementation() {
        this.requiredPlugin = "GriefPrevention";
        this.requiredField = new RequiredField<>("claims");
    }

    @Override
    public String getName() {
        return "GRIEFPREVENTION";
    }

    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        Location location = onlinePlayer.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Long> regions = this.requiredField.getValueList(jp, Long.class);
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        return regions.contains(claim.getID());
    }
}