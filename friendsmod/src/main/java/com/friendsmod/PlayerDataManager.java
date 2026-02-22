package com.friendsmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlayerDataManager {
    
    private static PlayerData playerData;
    private static List<Friend> friends;
    private static List<FriendRequest> friendRequests;
    private static File dataFile;
    private static File friendsFile;
    private static File requestsFile;
    private static ConcurrentMap<String, FriendStatus> friendStatusMap;
    private static boolean hasShownInvitePopup = false;
    
    public static void init() {
        dataFile = new File(MinecraftClient.getInstance().runDirectory, "friendsmod_data.json");
        friendsFile = new File(MinecraftClient.getInstance().runDirectory, "friendsmod_friends.json");
        requestsFile = new File(MinecraftClient.getInstance().runDirectory, "friendsmod_requests.json");
        
        friendStatusMap = new ConcurrentHashMap<>();
        
        loadPlayerData();
        loadFriends();
        loadFriendRequests();
    }
    
    private static void loadPlayerData() {
        if (!dataFile.exists()) {
            playerData = new PlayerData(
                "Player",
                generateUID(),
                "default",
                "Hello! I'm using Friends Mod",
                false,
                System.currentTimeMillis()
            );
            savePlayerData();
        } else {
            try (FileReader reader = new FileReader(dataFile)) {
                Gson gson = new Gson();
                playerData = gson.fromJson(reader, PlayerData.class);
                if (playerData == null) {
                    playerData = new PlayerData(
                        "Player",
                        generateUID(),
                        "default",
                        "Hello! I'm using Friends Mod",
                        false,
                        System.currentTimeMillis()
                    );
                }
            } catch (IOException e) {
                playerData = new PlayerData(
                    "Player",
                    generateUID(),
                    "default",
                    "Hello! I'm using Friends Mod",
                    false,
                    System.currentTimeMillis()
                );
            }
        }
    }
    
    private static void loadFriends() {
        if (!friendsFile.exists()) {
            friends = new ArrayList<>();
        } else {
            try (FileReader reader = new FileReader(friendsFile)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Friend>>() {}.getType();
                friends = gson.fromJson(reader, listType);
                if (friends == null) {
                    friends = new ArrayList<>();
                }
            } catch (IOException e) {
                friends = new ArrayList<>();
            }
        }
    }
    
    private static void loadFriendRequests() {
        if (!requestsFile.exists()) {
            friendRequests = new ArrayList<>();
        } else {
            try (FileReader reader = new FileReader(requestsFile)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FriendRequest>>() {}.getType();
                friendRequests = gson.fromJson(reader, listType);
                if (friendRequests == null) {
                    friendRequests = new ArrayList<>();
                }
            } catch (IOException e) {
                friendRequests = new ArrayList<>();
            }
        }
    }
    
    public static void savePlayerData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(playerData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveFriends() {
        try (FileWriter writer = new FileWriter(friendsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(friends, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveFriendRequests() {
        try (FileWriter writer = new FileWriter(requestsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(friendRequests, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static PlayerData getPlayerData() {
        return playerData;
    }
    
    public static List<Friend> getFriends() {
        return friends;
    }
    
    public static List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }
    
    public static void addFriend(Friend friend) {
        friends.add(friend);
        saveFriends();
    }
    
    public static void removeFriend(String uid) {
        friends.removeIf(friend -> friend.getUid().equals(uid));
        saveFriends();
    }
    
    public static void blockFriend(String uid) {
        for (Friend friend : friends) {
            if (friend.getUid().equals(uid)) {
                friend.setBlocked(true);
                break;
            }
        }
        saveFriends();
    }
    
    public static void unblockFriend(String uid) {
        for (Friend friend : friends) {
            if (friend.getUid().equals(uid)) {
                friend.setBlocked(false);
                break;
            }
        }
        saveFriends();
    }
    
    public static Friend getFriendByUid(String uid) {
        for (Friend friend : friends) {
            if (friend.getUid().equals(uid)) {
                return friend;
            }
        }
        return null;
    }
    
    public static FriendRequest getFriendRequestByUid(String uid) {
        for (FriendRequest request : friendRequests) {
            if (request.getUid().equals(uid)) {
                return request;
            }
        }
        return null;
    }
    
    public static void addFriendRequest(FriendRequest request) {
        friendRequests.add(request);
        saveFriendRequests();
    }
    
    public static void removeFriendRequest(String uid) {
        friendRequests.removeIf(request -> request.getUid().equals(uid));
        saveFriendRequests();
    }
    
    public static String generateUID() {
        return UUID.randomUUID().toString();
    }
    
    public static void setUsername(String username) {
        playerData.setUsername(username);
        savePlayerData();
    }
    
    public static void setSkin(String skin) {
        playerData.setSkin(skin);
        savePlayerData();
    }
    
    public static void setStatusMessage(String statusMessage) {
        playerData.setStatusMessage(statusMessage);
        savePlayerData();
    }
    
    public static void setOnlineStatus(boolean online) {
        playerData.setOnline(online);
        savePlayerData();
        updateFriendStatus(online);
    }
    
    public static boolean hasShownInvitePopup() {
        return hasShownInvitePopup;
    }
    
    public static void setHasShownInvitePopup(boolean value) {
        hasShownInvitePopup = value;
    }
    
    public static void updateFriendStatus(boolean online) {
        for (Friend friend : friends) {
            FriendStatus status = friendStatusMap.get(friend.getUid());
            if (status != null) {
                status.setOnline(online);
            }
        }
    }
    
    public static void setFriendStatus(String uid, FriendStatus status) {
        friendStatusMap.put(uid, status);
    }
    
    public static FriendStatus getFriendStatus(String uid) {
        return friendStatusMap.get(uid);
    }
    
    public static class PlayerData {
        private String username;
        private String uid;
        private String skin;
        private String statusMessage;
        private boolean online;
        private long lastOnline;
        
        public PlayerData(String username, String uid, String skin, String statusMessage, boolean online, long lastOnline) {
            this.username = username;
            this.uid = uid;
            this.skin = skin;
            this.statusMessage = statusMessage;
            this.online = online;
            this.lastOnline = lastOnline;
        }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getUid() { return uid; }
        public String getSkin() { return skin; }
        public void setSkin(String skin) { this.skin = skin; }
        public String getStatusMessage() { return statusMessage; }
        public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
        public boolean isOnline() { return online; }
        public void setOnline(boolean online) { this.online = online; }
        public long getLastOnline() { return lastOnline; }
        public void setLastOnline(long lastOnline) { this.lastOnline = lastOnline; }
    }
    
    public static class Friend {
        private String username;
        private String uid;
        private String skin;
        private boolean online;
        private String lastWorld;
        private boolean blocked;
        private String statusMessage;
        
        public Friend(String username, String uid, String skin) {
            this.username = username;
            this.uid = uid;
            this.skin = skin;
            this.online = false;
            this.lastWorld = "";
            this.blocked = false;
            this.statusMessage = "";
        }
        
        public String getUsername() { return username; }
        public String getUid() { return uid; }
        public String getSkin() { return skin; }
        public boolean isOnline() { return online; }
        public void setOnline(boolean online) { this.online = online; }
        public String getLastWorld() { return lastWorld; }
        public void setLastWorld(String lastWorld) { this.lastWorld = lastWorld; }
        public boolean isBlocked() { return blocked; }
        public void setBlocked(boolean blocked) { this.blocked = blocked; }
        public String getStatusMessage() { return statusMessage; }
        public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
    }
    
    public static class FriendRequest {
        private String username;
        private String uid;
        private String skin;
        private long timestamp;
        
        public FriendRequest(String username, String uid, String skin) {
            this.username = username;
            this.uid = uid;
            this.skin = skin;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getUsername() { return username; }
        public String getUid() { return uid; }
        public String getSkin() { return skin; }
        public long getTimestamp() { return timestamp; }
    }
    
    public static class FriendStatus {
        private boolean online;
        private String currentWorld;
        private String location;
        private long lastSeen;
        
        public FriendStatus(boolean online, String currentWorld, String location) {
            this.online = online;
            this.currentWorld = currentWorld;
            this.location = location;
            this.lastSeen = System.currentTimeMillis();
        }
        
        public boolean isOnline() { return online; }
        public void setOnline(boolean online) { this.online = online; }
        public String getCurrentWorld() { return currentWorld; }
        public void setCurrentWorld(String currentWorld) { this.currentWorld = currentWorld; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public long getLastSeen() { return lastSeen; }
        public void setLastSeen(long lastSeen) { this.lastSeen = lastSeen; }
    }
}