package com.gmail.picono435.picojobs.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldImplementation extends WorkZoneImplementation {

    public WorldImplementation() {
        this.requiredPlugin = Bukkit.getPluginManager().getPlugin("PicoJobs");
        this.requiredField = new RequiredField("world", RequiredField.RequiredFieldType.STRING);
    }

    @Override
    public String getName() {
        return "WORLD";
    }

    @Override
    public boolean isInWorkZone(Player player) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        String world = FileCreator.getJobsConfig().getString("jobs." + jp.getJob().getID() + "." + requiredField.getName());
        return player.getWorld().getName().equals(world);
    }
}
