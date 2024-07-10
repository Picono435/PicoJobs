package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BiomeImplementation extends WorkZoneImplementation {

    protected RequiredField<String, ResourceLocation> requiredField;

    public BiomeImplementation() {
        this.requiredField = new RequiredField<>("biomes", new RequiredFieldType<>(String.class, ResourceLocation.class) {
            @Override
            public ResourceLocation toValue(@Nonnull String primitive) {
                return new ResourceLocation(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(ResourceLocation value) {
                return value.toString();
            }

            @Nonnull
            @Override
            public List<ResourceLocation> getSuggestions() {
                Optional<HolderLookup.RegistryLookup<Biome>> lookup = VanillaRegistries.createLookup().lookup(Registries.BIOME);
                return lookup.map(biomeRegistryLookup -> biomeRegistryLookup.listTagIds().map(TagKey::location).toList()).orElseGet(List::of);
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
        List<ResourceLocation> regions = this.requiredField.getValueList(jp.getJob());
        Player onlinePlayer = PicoJobsMod.getServer().get().getPlayerList().getPlayer(player);
        return regions.contains(onlinePlayer.level().getBiome(onlinePlayer.getOnPos()).unwrapKey().get().location());
    }

    @Override
    public RequiredField<String, ResourceLocation> getRequiredField() {
        return requiredField;
    }
}
