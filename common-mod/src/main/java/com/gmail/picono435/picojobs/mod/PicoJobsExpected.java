package com.gmail.picono435.picojobs.mod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

public class PicoJobsExpected {

    @ExpectPlatform
    public static boolean hasPermission(Player player, String permission) {
        throw new AssertionError();
    }
}
