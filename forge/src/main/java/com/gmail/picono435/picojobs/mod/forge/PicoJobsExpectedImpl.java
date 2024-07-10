package com.gmail.picono435.picojobs.mod.forge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionNode;

public class PicoJobsExpectedImpl {
    public static boolean hasPermission(Player player, String permission) {
        PermissionNode<Boolean> permissionNode = null;
        if(permission.equals("picojobs.use.basic")) permissionNode = PicoJobsForge.PICOJOBS_USE_BASIC_PERM;
        if(permission.equals("picojobs.admin")) permissionNode = PicoJobsForge.PICOJOBS_ADMIN_PERM;
        if(permissionNode == null) return true;
        return PermissionAPI.getPermission((ServerPlayer) player, permissionNode);
    }
}
