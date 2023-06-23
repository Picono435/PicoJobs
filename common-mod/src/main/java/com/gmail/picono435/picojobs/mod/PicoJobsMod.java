package com.gmail.picono435.picojobs.mod;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.mod.command.ModJobsCommand;
import com.gmail.picono435.picojobs.mod.listeners.ModJoinCacheListener;
import com.gmail.picono435.picojobs.mod.listeners.jobs.*;
import com.gmail.picono435.picojobs.mod.platform.ModSchedulerAdapter;
import dev.architectury.event.events.common.*;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class PicoJobsMod {
    public static final String MOD_ID = "picojobs";
    private static final Logger logger = LogManager.getLogger(MOD_ID);
    private static MinecraftServer server;
    
    public static void init() {
        LifecycleEvent.SERVER_BEFORE_START.register((listener) -> {
            server = listener.getConnection().getServer();
            ((ModSchedulerAdapter)PicoJobsCommon.getSchedulerAdapter()).init();
            PicoJobsCommon.onEnable();
        });

        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            //TODO: Create commands
            System.out.println("Workin on it...");
            ModJobsCommand.register(dispatcher);
        });

        PlayerEvent.PLAYER_JOIN.register(new ModJoinCacheListener());


        //TODO: BlockEvent.BREAK.register(new BreakListener());
        PlayerEvent.CRAFT_ITEM.register(new CraftListener());
        //TODO: EAT JOB TYPE
        //TODO: Enchant JOB TYPE
        PlayerEvent.FILL_BUCKET.register(new FillListener());
        //TODO: FISH JOB TYPE
        EntityEvent.LIVING_DEATH.register(new KillEntityListener());
        EntityEvent.LIVING_DEATH.register(new KillListener());
        PlayerEvent.FILL_BUCKET.register(new MilkListener());
        //TODO: MOVE JOB TYPE
        BlockEvent.PLACE.register(new PlaceListener());
        //TODO: REPAIR JOB TYPE
        //TODO: SHEAR JOB TYPE
        PlayerEvent.SMELT_ITEM.register(new SmeltListener());
        //TODO: STRIP_LOGS JOB TYPE
        EntityEvent.ANIMAL_TAME.register(new TameListener());
        //TODO: TRADE LISTENER

    }

    public static Logger getLogger() {
        return logger;
    }

    public static Optional<MinecraftServer> getServer() {
        return Optional.ofNullable(server);
    }
}
