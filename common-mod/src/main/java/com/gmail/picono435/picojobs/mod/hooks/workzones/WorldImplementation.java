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

    public WorldImplementation() {
        this.requiredPlugin = "PicoJobs";
        this.requiredField = new RequiredField("worlds", RequiredField.RequiredFieldType.STRING_LIST);
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = null;
        try {
            regions = FileManager.getJobsNode().node("jobs", jp.getJob().getID(), requiredField.getName()).getList(String.class);
        } catch (SerializationException event) {
            throw new RuntimeException(event);
        }
        return regions.contains(((ServerLevelData)PicoJobsMod.getServer().get().getPlayerList().getPlayer(player).serverLevel().getLevelData()).getLevelName());
    }
}
