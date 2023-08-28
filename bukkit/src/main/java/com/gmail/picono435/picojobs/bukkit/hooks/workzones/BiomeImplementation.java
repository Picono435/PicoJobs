package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class BiomeImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public BiomeImplementation() {
        this.requiredField = new RequiredField<>("biomes");
    }

    @Override
    public String getName() {
        return "BIOME";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        Player onlinePlayer = Bukkit.getPlayer(player);
        return regions.contains(onlinePlayer.getWorld().getBiome(onlinePlayer.getLocation()).getKey().toString());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
