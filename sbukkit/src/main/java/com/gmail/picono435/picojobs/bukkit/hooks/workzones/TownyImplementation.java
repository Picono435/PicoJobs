package com.gmail.picono435.picojobs.bukkit.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.palmergames.bukkit.towny.TownyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class TownyImplementation extends WorkZoneImplementation {

    protected RequiredField<String> requiredField;

    public TownyImplementation() {
        this.requiredPlugin = "Towny";
        this.requiredField = new RequiredField<>("towns");
    }

    @Override
    public String getName() {
        return "TOWNY";
    }

    @Override
    public boolean isInWorkZone(UUID player) {
        Player onlinePlayer = Bukkit.getPlayer(player);
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> regions = this.requiredField.getValueList(jp, String.class);
        return regions.contains(TownyAPI.getInstance().getTown(onlinePlayer.getLocation()).getName());
    }
}
