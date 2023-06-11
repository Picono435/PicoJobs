package com.gmail.picono435.picojobs.hooks.workzones;

import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class BiomeImplementation extends WorkZoneImplementation {

    public BiomeImplementation() {
        this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PicoJobs");
        this.requiredField = new RequiredField("biomes", RequiredField.RequiredFieldType.STRING_LIST);
    }

    @Override
    public String getName() {
        return "BIOME";
    }

    @Override
    public boolean isInWorkZone(Player player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = FileCreator.getJobsConfig().getStringList("jobs." + jp.getJob().getID() + "." + requiredField.getName());
        return regions.contains(player.getWorld().getBiome(player.getLocation()).getKey().toString());
    }
}
