package com.gmail.picono435.picojobs.hooks.workzones;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.WorkZoneImplementation;
import com.gmail.picono435.picojobs.api.utils.RequiredField;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardImplementation extends WorkZoneImplementation {

    public WorldGuardImplementation() {
        this.requiredPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        this.requiredField = new RequiredField("region", RequiredField.RequiredFieldType.STRING);
    }

    @Override
    public String getName() {
        return "WORLDGUARD";
    }

    @Override
    public boolean isInWorkZone(Player player) {
        Location location = player.getLocation();
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        String region = FileCreator.getJobsConfig().getString("jobs." + jp.getJob().getID() + "." + requiredField.getName());
        String[] regionSplitted = region.split(":");
        if(regionSplitted.length > 1 && !regionSplitted[1].equals(location.getWorld().getName())) return false;
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));
        ApplicableRegionSet regionSet = regionManager.getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ()));
        return regionSet.getRegions().stream().filter(protectedRegion -> protectedRegion.getId().equals(regionSplitted[0])).count() >= 1;
    }
}
