package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import io.github.flemmli97.flan.claim.ClaimStorage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public class FlanImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public FlanImplementation() {
        this.requiredPlugin = "flan";
        this.requiredField = new RequiredField<>("claims");
    }

    @Override
    public String getName() {
        return "FLAN";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        ServerPlayer onlinePlayer = PicoJobsMod.getServer().get().getPlayerList().getPlayer(player);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        if(regions.contains(ClaimStorage.get(onlinePlayer.serverLevel()).getClaimAt(onlinePlayer.getOnPos()).getClaimID().toString())) return true;
        return false;
    }
}
