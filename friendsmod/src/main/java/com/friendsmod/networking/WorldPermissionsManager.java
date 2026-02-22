package com.friendsmod.networking;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class WorldPermissionsManager {
    
    private static final Map<String, PermissionSet> playerPermissions = new HashMap<>();
    
    public static void setPermissions(String playerUid, PermissionSet permissions) {
        playerPermissions.put(playerUid, permissions);
    }
    
    public static PermissionSet getPermissions(String playerUid) {
        return playerPermissions.get(playerUid);
    }
    
    public static boolean canBreakBlock(String playerUid, ServerPlayerEntity player, BlockPos pos) {
        PermissionSet permissions = getPermissions(playerUid);
        if (permissions == null) {
            return true; // Default allow
        }
        return permissions.canBreakBlocks();
    }
    
    public static boolean canPlaceBlock(String playerUid, ServerPlayerEntity player, BlockPos pos) {
        PermissionSet permissions = getPermissions(playerUid);
        if (permissions == null) {
            return true; // Default allow
        }
        return permissions.canPlaceBlocks();
    }
    
    public static boolean canAccessChest(String playerUid, ServerPlayerEntity player, BlockPos pos) {
        PermissionSet permissions = getPermissions(playerUid);
        if (permissions == null) {
            return true; // Default allow
        }
        return permissions.canAccessChests();
    }
    
    public static boolean canDamageMobs(String playerUid, ServerPlayerEntity player) {
        PermissionSet permissions = getPermissions(playerUid);
        if (permissions == null) {
            return true; // Default allow
        }
        return permissions.canDamageMobs();
    }
    
    public static void clearPermissions(String playerUid) {
        playerPermissions.remove(playerUid);
    }
    
    public static class PermissionSet {
        private boolean canBreakBlocks;
        private boolean canPlaceBlocks;
        private boolean canAccessChests;
        private boolean canDamageMobs;
        
        public PermissionSet(boolean canBreakBlocks, boolean canPlaceBlocks, boolean canAccessChests, boolean canDamageMobs) {
            this.canBreakBlocks = canBreakBlocks;
            this.canPlaceBlocks = canPlaceBlocks;
            this.canAccessChests = canAccessChests;
            this.canDamageMobs = canDamageMobs;
        }
        
        public boolean canBreakBlocks() { return canBreakBlocks; }
        public boolean canPlaceBlocks() { return canPlaceBlocks; }
        public boolean canAccessChests() { return canAccessChests; }
        public boolean canDamageMobs() { return canDamageMobs; }
        
        public void setCanBreakBlocks(boolean canBreakBlocks) { this.canBreakBlocks = canBreakBlocks; }
        public void setCanPlaceBlocks(boolean canPlaceBlocks) { this.canPlaceBlocks = canPlaceBlocks; }
        public void setCanAccessChests(boolean canAccessChests) { this.canAccessChests = canAccessChests; }
        public void setCanDamageMobs(boolean canDamageMobs) { this.canDamageMobs = canDamageMobs; }
    }
}