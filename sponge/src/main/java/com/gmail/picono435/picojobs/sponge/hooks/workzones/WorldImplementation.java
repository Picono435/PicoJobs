package com.gmail.picono435.picojobs.sponge.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String, ServerWorld> requiredField;

    public WorldImplementation() {
        this.requiredField = new RequiredField<>("worlds", new RequiredFieldType<String, ServerWorld>(String.class, ServerWorld.class) {
            @Override
            @Nullable
            public ServerWorld toValue(@Nonnull String primitive) {
                return Sponge.server().worldManager().world(ResourceKey.resolve(primitive)).orElse(null);
            }

            @Nonnull
            @Override
            public String toPrimitive(@Nullable ServerWorld value) {
                return Sponge.server().worldManager().worldKey(value.uniqueId()).map(ResourceKey::asString).get();
            }

            @Nonnull
            @Override
            public List<ServerWorld> getSuggestions() {
                return new ArrayList<>(Sponge.server().worldManager().worlds());
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
        List<ServerWorld> regions = this.requiredField.getValueList(jp.getJob());
        return regions.contains(Sponge.server().player(player).get().serverLocation().world());
    }

    @Override
    public RequiredField<String, ServerWorld> getRequiredField() {
        return requiredField;
    }
}
