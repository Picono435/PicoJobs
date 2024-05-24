package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String, ServerLevel> requiredField;

    public WorldImplementation() {
        this.requiredField = new RequiredField<>("worlds", new RequiredFieldType<>(String.class, ServerLevel.class) {
            @Override
            public ServerLevel toValue(@Nonnull String primitive) {
                return PicoJobsMod.getServer().get().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(primitive)));
            }

            @Nonnull
            @Override
            public String toPrimitive(ServerLevel value) {
                return value.dimension().location().toString();
            }

            @Nonnull
            @Override
            public List<ServerLevel> getSuggestions() {
                return ((Collection<ServerLevel>)PicoJobsMod.getServer().get().getAllLevels()).stream().toList();
            }
        }, true);
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<ServerLevel> regions = this.requiredField.getValueList(jp.getJob());
        return regions.contains(PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).serverLevel());
    }

    @Override
    public RequiredField<String, ServerLevel> getRequiredField() {
        return requiredField;
    }
}
