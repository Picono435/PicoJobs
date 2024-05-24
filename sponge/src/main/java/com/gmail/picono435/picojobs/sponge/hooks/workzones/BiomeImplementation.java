package com.gmail.picono435.picojobs.sponge.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.biome.Biomes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BiomeImplementation extends WorkZoneImplementation {

    protected RequiredField<String, Biome> requiredField;

    public BiomeImplementation() {
        this.requiredField = new RequiredField<>("biomes", new RequiredFieldType<String, Biome>(String.class, Biome.class) {
            @Nullable
            @Override
            public Biome toValue(@Nonnull String primitive) {
                return Sponge.game().registry(RegistryTypes.BIOME).value(ResourceKey.resolve(primitive));
            }

            @Nonnull
            @Override
            public String toPrimitive(@Nullable Biome value) {
                return Sponge.game().registry(RegistryTypes.BIOME).valueKey(value).asString();
            }

            @Nonnull
            @Override
            public List<Biome> getSuggestions() {
                return Sponge.game().registry(RegistryTypes.BIOME).stream().collect(Collectors.toList());
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
        Player onlinePlayer = Sponge.server().player(player).get();
        return regions.contains(onlinePlayer.world().biome(onlinePlayer.blockPosition()));
    }

    @Override
    public RequiredField<String, Biome> getRequiredField() {
        return requiredField;
    }
}
