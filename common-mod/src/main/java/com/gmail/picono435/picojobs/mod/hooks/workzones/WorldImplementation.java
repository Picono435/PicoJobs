package com.gmail.picono435.picojobs.mod.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class WorldImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public WorldImplementation() {
        this.requiredField = new RequiredField<>("worlds");
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        return regions.contains(((ServerLevelData)PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).serverLevel().getLevelData()).getLevelName());
    }

    @Override
    public RequiredField<String> getRequiredField() {
        return requiredField;
    }
}
