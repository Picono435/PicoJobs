package com.gmail.picono435.picojobs.mod.forge;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import com.gmail.picono435.picojobs.common.platform.Platform;
import com.gmail.picono435.picojobs.mod.forge.platform.ForgePlaceholderTranslator;
import com.gmail.picono435.picojobs.mod.forge.platform.ForgePlatformAdapter;
import com.gmail.picono435.picojobs.mod.platform.ModColorConverter;
import com.gmail.picono435.picojobs.mod.platform.ModSchedulerAdapter;
import com.gmail.picono435.picojobs.mod.platform.ModSoftwareHooker;
import com.gmail.picono435.picojobs.mod.platform.ModWhitelistConverter;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

@Mod(PicoJobsMod.MOD_ID)
public class PicoJobsForge {
    public static PermissionNode<Boolean> PICOJOBS_USE_BASIC_PERM = new PermissionNode<>(
            PicoJobsMod.MOD_ID,
            "picojobs.use.basic",
            PermissionTypes.BOOLEAN,
            (serverPlayer, uuid, permissionDynamicContexts) -> true);
    public static PermissionNode<Boolean> PICOJOBS_ADMIN_PERM = new PermissionNode<>(
            PicoJobsMod.MOD_ID,
            "picojobs.admin",
            PermissionTypes.BOOLEAN,
            (serverPlayer, uuid, permissionDynamicContexts) -> serverPlayer.hasPermissions(3));


    public PicoJobsForge() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        EventBuses.registerModEventBus(PicoJobsMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        PicoJobsCommon.onLoad(
                "1.0-pre",
                Platform.FORGE,
                LoggerFactory.getLogger(PicoJobsMod.getLogger().getName()),
                FMLPaths.CONFIGDIR.get().resolve("PicoJobs").toFile(),
                null,
                new ModSchedulerAdapter(),
                new ForgePlatformAdapter(),
                new ModColorConverter(),
                new ForgePlaceholderTranslator(),
                new ModWhitelistConverter(),
                new ModSoftwareHooker()
        );

        PicoJobsMod.init();
    }

    @SubscribeEvent
    public void permission(PermissionGatherEvent.Nodes event) {
        event.addNodes(PICOJOBS_USE_BASIC_PERM, PICOJOBS_ADMIN_PERM);
    }
}
