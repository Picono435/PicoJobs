package com.gmail.picono435.picojobs.mod.fabric;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import com.gmail.picono435.picojobs.mod.fabric.hooks.PlaceholdersHook;
import com.gmail.picono435.picojobs.mod.fabric.platform.FabricPlaceholderTranslator;
import com.gmail.picono435.picojobs.mod.fabric.platform.FabricPlatformAdapter;
import com.gmail.picono435.picojobs.mod.platform.ModWhitelistConverter;
import com.gmail.picono435.picojobs.mod.platform.ModColorConverter;
import com.gmail.picono435.picojobs.mod.platform.ModSchedulerAdapter;
import com.gmail.picono435.picojobs.mod.platform.ModSoftwareHooker;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class PicoJobsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLauncherBase.getLauncher().setValidParentClassPath(Collections.singleton(FabricLoader.getInstance().getConfigDir().resolve("PicoJobs").resolve("libraries")));

        PicoJobsCommon.onLoad(
                FabricLoader.getInstance().getModContainer(PicoJobsMod.MOD_ID).get().getMetadata().getVersion().getFriendlyString(),
                Platform.FABRIC,
                LoggerFactory.getLogger(PicoJobsMod.getLogger().getName()),
                FabricLoader.getInstance().getConfigDir().resolve("PicoJobs").toFile(),
                null,
                new ModSchedulerAdapter(),
                new FabricPlatformAdapter(),
                new ModColorConverter(),
                new FabricPlaceholderTranslator(),
                new ModWhitelistConverter(),
                new ModSoftwareHooker()
        );
        PicoJobsMod.init();

        PlaceholdersHook.registerPlaceholders();
    }
}
