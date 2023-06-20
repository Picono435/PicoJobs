package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinCacheListener implements Listener {

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!PicoJobsAPI.getStorageManager().getCacheManager().playerExists(player.getUniqueId()) || PicoJobsAPI.getSettingsManager().isResetCacheOnJoin()) {
            Bukkit.getScheduler().runTaskAsynchronously(PicoJobsBukkit.getInstance(), () -> {
                try {
                    JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayerFromStorage(player.getUniqueId()).get();
                    PicoJobsAPI.getStorageManager().getCacheManager().addToCache(jp);
                } catch (Exception ex) {
                    PicoJobsCommon.getLogger().severe("Error connecting to the storage. The plugin will not work correctly.");
                    ex.printStackTrace();
                }
            });
        }
    }
}
