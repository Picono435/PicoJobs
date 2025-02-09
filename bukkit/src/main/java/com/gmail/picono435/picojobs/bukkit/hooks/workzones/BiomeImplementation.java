package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// This is a LEGACY Biome implementation that only works for minecraft versions 1.21.3 and later
public class BiomeImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Biome> requiredField;

    @Override
    public String getName() {
        return "BIOME";
    }

    @Override
    public void onRegister() {
        this.requiredField = new RequiredField<>("biomes", new RequiredFieldType<String, Biome>(String.class, Biome.class) {
            @Override
            public Biome toValue(@Nonnull String primitive) {
                return PicoJobsBukkit.getNamespacedUtils().matchBiome(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(Biome value) {
                return PicoJobsBukkit.getNamespacedUtils().getKeyFromObject(value);
            }

            @Nonnull
            @Override
            public List<Biome> getSuggestions() {
                return PicoJobsBukkit.getNamespacedUtils().getBiomes();
            }
        }, true);
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<Biome> regions = this.requiredField.getValueList(jp.getJob());
        Player onlinePlayer = Bukkit.getPlayer(player);
        return regions.contains(onlinePlayer.getWorld().getBiome(onlinePlayer.getLocation().getBlockX(), onlinePlayer.getLocation().getBlockZ()));
    }

    @Override
    public RequiredField<String, Biome> getRequiredField() {
        return requiredField;
    }
}
