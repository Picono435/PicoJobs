package com.gmail.picono435.picojobs.common.listeners;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;

import java.util.UUID;

public class JoinCacheListener {

    public static void onJoin(UUID player) {
        if(!PicoJobsAPI.getStorageManager().getCacheManager().playerExists(player) || PicoJobsAPI.getSettingsManager().isResetCacheOnJoin()) {
            PicoJobsCommon.getSchedulerAdapter().executeAsync(() -> {
                try {
                    JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayerFromStorage(player).get();
                    PicoJobsAPI.getStorageManager().getCacheManager().addToCache(jp);
                } catch (Exception ex) {
                    PicoJobsCommon.getLogger().error("Error connecting to the storage. The plugin will not work correctly.");
                    ex.printStackTrace();
                }
            });
        }
    }
}
