package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class BiomeImplementation extends WorkZoneImplementation {

    public BiomeImplementation() {
        this.requiredPlugin = "PicoJobs";
        this.requiredField = new RequiredField("biomes", RequiredField.RequiredFieldType.STRING_LIST);
    }

    @Override
    public String getName() {
        return "BIOME";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = null;
        try {
            regions = FileManager.getJobsNode().node("jobs", jp.getJob().getID(), requiredField.getName()).getList(String.class);
        } catch (SerializationException event) {
            throw new RuntimeException(event);
        }
        Player onlinePlayer = Bukkit.getPlayer(player);
        return regions.contains(onlinePlayer.getWorld().getBiome(onlinePlayer.getLocation()).getKey().toString());
    }
}
