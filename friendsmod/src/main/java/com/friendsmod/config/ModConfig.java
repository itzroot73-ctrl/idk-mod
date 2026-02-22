package com.friendsmod.config;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModConfig {
    
    private static KeyBinding friendsKeyBinding;
    private static KeyBinding pushToTalkKey;
    private static KeyBinding chatKey;
    private static KeyBinding friendListKey;
    
    private static boolean voiceChatEnabled = true;
    private static boolean pushToTalkEnabled = false;
    private static boolean darkModeEnabled = false;
    private static boolean blurEnabled = true;
    private static boolean soundEnabled = true;
    
    public static void init() {
        // Initialize key bindings
        friendsKeyBinding = new KeyBinding(
            "key.friendsmod.friends", 
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.friendsmod"
        );
        
        pushToTalkKey = new KeyBinding(
            "key.friendsmod.push_to_talk", 
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.friendsmod"
        );
        
        chatKey = new KeyBinding(
            "key.friendsmod.chat", 
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            "category.friendsmod"
        );
        
        friendListKey = new KeyBinding(
            "key.friendsmod.friend_list", 
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_TAB,
            "category.friendsmod"
        );
    }
    
    public static KeyBinding getFriendsKeyBinding() {
        return friendsKeyBinding;
    }
    
    public static KeyBinding getPushToTalkKey() {
        return pushToTalkKey;
    }
    
    public static KeyBinding getChatKey() {
        return chatKey;
    }
    
    public static KeyBinding getFriendListKey() {
        return friendListKey;
    }
    
    public static boolean isVoiceChatEnabled() {
        return voiceChatEnabled;
    }
    
    public static void setVoiceChatEnabled(boolean enabled) {
        voiceChatEnabled = enabled;
    }
    
    public static boolean isPushToTalkEnabled() {
        return pushToTalkEnabled;
    }
    
    public static void setPushToTalkEnabled(boolean enabled) {
        pushToTalkEnabled = enabled;
    }
    
    public static boolean isDarkModeEnabled() {
        return darkModeEnabled;
    }
    
    public static void setDarkModeEnabled(boolean enabled) {
        darkModeEnabled = enabled;
    }
    
    public static boolean isBlurEnabled() {
        return blurEnabled;
    }
    
    public static void setBlurEnabled(boolean enabled) {
        blurEnabled = enabled;
    }
    
    public static boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
    }
}