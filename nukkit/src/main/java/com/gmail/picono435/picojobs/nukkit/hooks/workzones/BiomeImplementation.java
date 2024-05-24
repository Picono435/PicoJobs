package com.gmail.picono435.picojobs.nukkit.hooks.workzones;

import cn.nukkit.Player;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.biome.EnumBiome;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BiomeImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Biome> requiredField;

    public BiomeImplementation() {
        this.requiredField = new RequiredField<>("biomes", new RequiredFieldType<String, Biome>(String.class, Biome.class) {
            @Override
            public Biome toValue(@Nonnull String primitive) {
                return Biome.getBiome(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(Biome value) {
                return Biome.getBiomeNameFromId(value.getId());
            }

            @Nonnull
            @Override
            public List<Biome> getSuggestions() {
                return Arrays.stream(EnumBiome.values()).map(enumBiome -> enumBiome.biome).collect(Collectors.toList());
            }
        }, true);
    }

    @Override
    public String getName() {
        return "BIOME";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Biome> regions = this.requiredField.getValueList(jp.getJob());
        Player onlinePlayer = PicoJobsNukkit.getInstance().getServer().getPlayer(player).get();
        return regions.contains(Biome.getBiome(onlinePlayer.getLevel().getBiomeId((int) onlinePlayer.getLocation().getX(), (int) onlinePlayer.getLocation().getZ())));
    }

    @Override
    public RequiredField<String, Biome> getRequiredField() {
        return requiredField;
    }
}
