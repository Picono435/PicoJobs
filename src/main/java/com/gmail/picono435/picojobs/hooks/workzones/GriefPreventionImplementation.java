package com.gmail.picono435.picojobs.hooks.workzones;

import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class GriefPreventionImplementation extends WorkZoneImplementation {

    public GriefPreventionImplementation() {
        this.requiredPlugin = Bukkit.getPluginManager().getPlugin("GriefPrevention");
        this.requiredField = new RequiredField("claims", RequiredField.RequiredFieldType.LONG_LIST);
    }

    @Override
    public String getName() {
        return "GRIEFPREVENTION";
    }

    public boolean isInWorkZone(Player player) {
        Location location = player.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Long> regions = FileCreator.getJobsConfig().getLongList("jobs." + jp.getJob().getID() + "." + requiredField.getName());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        return regions.contains(claim.getID());
    }
}