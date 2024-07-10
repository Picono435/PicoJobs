package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.RequiredFieldType;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import io.github.flemmli97.flan.claim.ClaimStorage;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class FlanImplementation extends WorkZoneImplementation {

    protected RequiredField<String, UUID> requiredField;

    public FlanImplementation() {
        this.requiredPlugin = "flan";
        this.requiredField = new RequiredField<>("claims", new RequiredFieldType<>(String.class, UUID.class) {
            @Override
            public UUID toValue(@Nonnull String primitive) {
                return UUID.fromString(primitive);
            }

            @Nonnull
            @Override
            public String toPrimitive(UUID value) {
                return value.toString();
            }

            @Nonnull
            @Override
            public List<UUID> getSuggestions() {
                return ClaimStorage.get(PicoJobsMod.getServer().get().overworld()).getClaims().keySet().stream().toList();
            }
        }, true);
    }

    @Override
    public String getName() {
        return "FLAN";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        ServerPlayer onlinePlayer = PicoJobsMod.getServer().get().getPlayerList().getPlayer(player);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<UUID> regions = this.requiredField.getValueList(jp.getJob());
        if(regions.contains(ClaimStorage.get(onlinePlayer.serverLevel()).getClaimAt(onlinePlayer.getOnPos()).getClaimID())) return true;
        return false;
    }

    @Override
    public RequiredField<String, UUID> getRequiredField() {
        return requiredField;
    }
}
