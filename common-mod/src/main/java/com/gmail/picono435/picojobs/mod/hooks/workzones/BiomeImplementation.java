package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.world.entity.player.Player;

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
        Player onlinePlayer = PicoJobsMod.getServer().get().getPlayerList().getPlayer(player);
        return regions.contains(onlinePlayer.level().getBiome(onlinePlayer.getOnPos()).unwrapKey().get().location().toString());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
