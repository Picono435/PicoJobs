package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.PicoJobsMain;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.file.FileManager;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReloadCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("reload", LanguageManager.getSubCommandAlias("reload"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        if(!PicoJobsCommon.getFileManager().init()) {
            sender.sendMessage(LanguageManager.getMessage("unknow-error", sender.getUUID()));
            return true;
        }
        try {
            PicoJobsCommon.getMainInstance().generateJobsFromConfig();
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
        PicoJobsAPI.getStorageManager().destroyStorageFactory();
        PicoJobsAPI.getStorageManager().initializeStorageFactory();
        for(UUID uuid : PicoJobsAPI.getStorageManager().getCacheManager().getAllFromCache()) {
            if(PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).getJob() == null) continue;
            PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).setJob(PicoJobsAPI.getJobsManager().getJob(PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).getJob().getID()));
        }
        sender.sendMessage(LanguageManager.getMessage("reload-command", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
