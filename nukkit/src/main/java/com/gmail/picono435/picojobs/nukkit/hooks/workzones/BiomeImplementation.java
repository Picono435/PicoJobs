package com.gmail.picono435.picojobs.nukkit.hooks.workzones;

import cn.nukkit.Player;
import cn.nukkit.level.biome.Biome;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

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
        Player onlinePlayer = PicoJobsNukkit.getInstance().getServer().getPlayer(player).get();
        return regions.contains(Biome.getBiomeNameFromId(onlinePlayer.getLevel().getBiomeId((int) onlinePlayer.getLocation().getX(), (int) onlinePlayer.getLocation().getZ())));
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
