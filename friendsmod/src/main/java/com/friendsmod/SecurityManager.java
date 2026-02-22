package com.friendsmod;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class SecurityManager {
    
    private static final long INVITE_COOLDOWN_MILLIS = TimeUnit.MINUTES.toMillis(5);
    private static final Map<String, Long> inviteCooldowns = new java.util.concurrent.ConcurrentHashMap<>();
    
    public static String hashUID(String uid) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(uid.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found: " + e.getMessage());
            return uid; // Fallback to original UID
        }
    }
    
    public static boolean isInviteOnCooldown(String senderUid) {
        Long lastInviteTime = inviteCooldowns.get(senderUid);
        if (lastInviteTime == null) {
            return false;
        }
        return System.currentTimeMillis() - lastInviteTime < INVITE_COOLDOWN_MILLIS;
    }
    
    public static void recordInvite(String senderUid) {
        inviteCooldowns.put(senderUid, System.currentTimeMillis());
    }
    
    public static void clearInviteCooldown(String senderUid) {
        inviteCooldowns.remove(senderUid);
    }
    
    public static boolean validateUID(String uid) {
        // Basic UID validation
        return uid != null && uid.matches("[a-zA-Z0-9_-]{8,36}");
    }
    
    public static String generateSecureToken() {
        // Generate a secure random token
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
    
    public static boolean isFriendRequestValid(String senderUid, String receiverUid) {
        // Check if friend request is valid (not blocked, not already friends, etc.)
        com.friendsmod.PlayerDataManager.Friend friend = com.friendsmod.PlayerDataManager.getFriendByUid(receiverUid);
        if (friend != null) {
            return false; // Already friends
        }
        
        com.friendsmod.PlayerDataManager.Friend blockedFriend = com.friendsmod.PlayerDataManager.getFriendByUid(senderUid);
        if (blockedFriend != null && blockedFriend.isBlocked()) {
            return false; // Blocked
        }
        
        return true;
    }
}