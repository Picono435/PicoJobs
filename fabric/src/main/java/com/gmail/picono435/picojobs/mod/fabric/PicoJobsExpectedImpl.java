package com.gmail.picono435.picojobs.mod.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class PicoJobsExpectedImpl {
    public static boolean hasPermission(Player player, String permission) {
        int defaultValue = 0;
        if(permission.equals("picojobs.admin")) defaultValue = 3;
        return Permissions.check(player, permission, defaultValue);
    }
}
