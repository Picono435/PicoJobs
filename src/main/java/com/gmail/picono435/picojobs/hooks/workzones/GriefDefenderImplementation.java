package com.gmail.picono435.picojobs.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class GriefDefenderImplementation extends WorkZoneImplementation {

    public GriefDefenderImplementation() {
        this.requiredPlugin = Bukkit.getPluginManager().getPlugin("GriefDefender");
        this.requiredField = new RequiredField("claim", RequiredField.RequiredFieldType.STRING_LIST);
    }

    @Override
    public String getName() {
        return "GRIEFDEFENDER";
    }

    public boolean isInWorkZone(Player player) {
        Location location = player.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = FileCreator.getJobsConfig().getStringList("jobs." + jp.getJob().getID() + "." + requiredField.getName());
        Claim claim = GriefDefender.getCore().getClaimAt(player.getLocation());
        if(claim == null || claim.isWilderness()) return regions.contains("wilderness");
        return regions.contains(claim.toString());
    }
}