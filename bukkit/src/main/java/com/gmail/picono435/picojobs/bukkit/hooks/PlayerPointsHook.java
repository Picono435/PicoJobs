package com.gmail.picono435.picojobs.bukkit.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.bukkit.hooks.economy.PointsImplementation;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerPointsHook {
    private static boolean isEnabled = false;
    private static PlayerPoints playerPoints;

    public static void setupPlayerPoints() {
        if(Bukkit.getPluginManager().getPlugin("PlayerPoints") == null) {
            return;
        }
        isEnabled = true;
        hookPlayerPoints();
        PicoJobsAPI.registerEconomy(new PointsImplementation());
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    private static boolean hookPlayerPoints() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    public static PlayerPointsAPI getPlayerPointsAPI() {
        return playerPoints.getAPI();
    }
}
