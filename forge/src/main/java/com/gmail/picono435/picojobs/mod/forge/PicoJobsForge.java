package com.gmail.picono435.picojobs.mod.forge;

import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import com.gmail.picono435.picojobs.common.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PicoJobsMod.MOD_ID)
public class PicoJobsForge {
    public PicoJobsForge() {
        EventBuses.registerModEventBus(PicoJobsMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        //PicoJobsMod.init(Platform.FORGE);
    }
}
