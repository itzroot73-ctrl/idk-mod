package com.friendsmod.api;

import com.friendsmod.PlayerDataManager;
import com.friendsmod.SecurityManager;
import com.friendsmod.voicechat.VoiceChatManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;
import java.util.Map;

public class FriendsModAPI {
    
    private static FriendsModAPI instance;
    
    public static FriendsModAPI getInstance() {
        if (instance == null) {
            instance = new FriendsModAPI();
        }
        return instance;
    }
    
    // Player Data API
    public String getPlayerUsername() {
        return com.friendsmod.PlayerDataManager.getPlayerData().getUsername();
    }
    
    public String getPlayerUID() {
        return com.friendsmod.PlayerDataManager.getPlayerData().getUid();
    }
    
    public String getPlayerSkin() {
        return com.friendsmod.PlayerDataManager.getPlayerData().getSkin();
    }
    
    public String getPlayerStatusMessage() {
        return com.friendsmod.PlayerDataManager.getPlayerData().getStatusMessage();
    }
    
    public boolean isPlayerOnline() {
        return com.friendsmod.PlayerDataManager.getPlayerData().isOnline();
    }
    
    // Friend Management API
    public List<com.friendsmod.PlayerDataManager.Friend> getFriends() {
        return com.friendsmod.PlayerDataManager.getFriends();
    }
    
    public com.friendsmod.PlayerDataManager.Friend getFriendByUID(String uid) {
        return com.friendsmod.PlayerDataManager.getFriendByUid(uid);
    }
    
    public void addFriend(String username, String uid, String skin) {
        com.friendsmod.PlayerDataManager.Friend friend = new com.friendsmod.PlayerDataManager.Friend(username, uid, skin);
        com.friendsmod.PlayerDataManager.addFriend(friend);
    }
    
    public void removeFriend(String uid) {
        com.friendsmod.PlayerDataManager.removeFriend(uid);
    }
    
    public void blockFriend(String uid) {
        com.friendsmod.PlayerDataManager.blockFriend(uid);
    }
    
    public void unblockFriend(String uid) {
        com.friendsmod.PlayerDataManager.unblockFriend(uid);
    }
    
    public boolean isFriendBlocked(String uid) {
        com.friendsmod.PlayerDataManager.Friend friend = com.friendsmod.PlayerDataManager.getFriendByUid(uid);
        return friend != null && friend.isBlocked();
    }
    
    // Friend Request API
    public List<com.friendsmod.PlayerDataManager.FriendRequest> getFriendRequests() {
        return com.friendsmod.PlayerDataManager.getFriendRequests();
    }
    
    public void sendFriendRequest(String username, String uid, String skin) {
        com.friendsmod.PlayerDataManager.FriendRequest request = new com.friendsmod.PlayerDataManager.FriendRequest(username, uid, skin);
        com.friendsmod.PlayerDataManager.addFriendRequest(request);
    }
    
    public void acceptFriendRequest(String uid) {
        com.friendsmod.PlayerDataManager.FriendRequest request = com.friendsmod.PlayerDataManager.getFriendRequestByUid(uid);
        if (request != null) {
            com.friendsmod.PlayerDataManager.Friend friend = new com.friendsmod.PlayerDataManager.Friend(request.getUsername(), request.getUid(), request.getSkin());
            com.friendsmod.PlayerDataManager.addFriend(friend);
            com.friendsmod.PlayerDataManager.removeFriendRequest(uid);
        }
    }
    
    public void rejectFriendRequest(String uid) {
        com.friendsmod.PlayerDataManager.removeFriendRequest(uid);
    }
    
    // Voice Chat API
    public boolean isVoiceChatEnabled() {
        return com.friendsmod.FriendsMod.voiceChatManager.isEnabled();
    }
    
    public void toggleVoiceChat() {
        com.friendsmod.FriendsMod.toggleVoiceChat();
    }
    
    public void setFriendVolume(String friendUid, float volume) {
        com.friendsmod.FriendsMod.voiceChatManager.setFriendVolume(friendUid, volume);
    }
    
    public float getFriendVolume(String friendUid) {
        return com.friendsmod.FriendsMod.voiceChatManager.getFriendVolume(friendUid);
    }
    
    public void muteAllFriends() {
        com.friendsmod.FriendsMod.voiceChatManager.muteAll();
    }
    
    public void unmuteAllFriends() {
        com.friendsmod.FriendsMod.voiceChatManager.unmuteAll();
    }
    
    // Security API
    public String hashUID(String uid) {
        return com.friendsmod.SecurityManager.hashUID(uid);
    }
    
    public boolean validateUID(String uid) {
        return com.friendsmod.SecurityManager.validateUID(uid);
    }
    
    public boolean isInviteOnCooldown(String senderUid) {
        return com.friendsmod.SecurityManager.isInviteOnCooldown(senderUid);
    }
    
    public void recordInvite(String senderUid) {
        com.friendsmod.SecurityManager.recordInvite(senderUid);
    }
    
    // Utility API
    public boolean isFriendOnline(String uid) {
        com.friendsmod.PlayerDataManager.Friend friend = com.friendsmod.PlayerDataManager.getFriendByUid(uid);
        return friend != null && friend.isOnline();
    }
    
    public String getFriendWorld(String uid) {
        com.friendsmod.PlayerDataManager.Friend friend = com.friendsmod.PlayerDataManager.getFriendByUid(uid);
        return friend != null ? friend.getLastWorld() : "";
    }
    
    public void sendMessageToFriend(String friendUid, String message) {
        com.friendsmod.NetworkManager.sendChatMessage(friendUid, message);
    }
    
    public void updatePlayerStatus(boolean online, String currentWorld) {
        com.friendsmod.PlayerDataManager.setOnlineStatus(online);
        // Update friend status
    }
    
    // Configuration API
    public void setKeyBinding(String keyName, String key) {
        // Set key binding for various functions
    }
    
    public Map<String, String> getAllTranslations() {
        // Return all available translations
        return com.friendsmod.LanguageManager.translations;
    }
    
    public String translate(String key) {
        return com.friendsmod.LanguageManager.translate(key);
    }
    
    // Event System (for addon mods)
    public void onFriendAdded(com.friendsmod.PlayerDataManager.Friend friend) {
        // Called when a friend is added
    }
    
    public void onFriendRemoved(String uid) {
        // Called when a friend is removed
    }
    
    public void onFriendRequestReceived(com.friendsmod.PlayerDataManager.FriendRequest request) {
        // Called when a friend request is received
    }
    
    public void onFriendRequestAccepted(String uid) {
        // Called when a friend request is accepted
    }
    
    public void onFriendRequestRejected(String uid) {
        // Called when a friend request is rejected
    }
    
    public void onFriendStatusChanged(String uid, boolean online, String world) {
        // Called when a friend's status changes
    }
    
    public void onChatMessageReceived(String senderUid, String message) {
        // Called when a chat message is received
    }
    
    public void onVoiceChatToggled(boolean enabled) {
        // Called when voice chat is toggled
    }
}