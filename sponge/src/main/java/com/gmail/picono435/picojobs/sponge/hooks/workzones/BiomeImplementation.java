package com.gmail.picono435.picojobs.sponge.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.biome.Biomes;

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
        Player onlinePlayer = Sponge.server().player(player).get();
        return regions.contains(Biomes.registry(onlinePlayer.serverLocation().world()).findValueKey(onlinePlayer.world().biome(onlinePlayer.position().toInt())).get().asString());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
