package com.gmail.picono435.picojobs.mod.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class PicoJobsExpectedImpl {
    public static boolean hasPermission(Player player, String permission) {
        boolean defaultValue = false;
        if(permission.equals("picojobs.use.basic")) defaultValue = true;
        return Permissions.check(player, permission, defaultValue);
    }
}
