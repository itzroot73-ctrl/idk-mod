package com.friendsmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageManager {
    
    private static final Map<String, String> translations = new HashMap<>();
    private static String currentLanguage = "en_us";
    
    public static void init() {
        loadTranslations();
    }
    
    private static void loadTranslations() {
        // Load English translations (default)
        translations.put("screen.friendsmod.login", "Friends Mod Login");
        translations.put("screen.friendsmod.friends", "Friends");
        translations.put("screen.friendsmod.add_friend", "Add Friend");
        translations.put("screen.friendsmod.friend_request", "Friend Request");
        translations.put("screen.friendsmod.friend_requests", "Friend Requests");
        translations.put("screen.friendsmod.manage_friends", "Manage Friends");
        translations.put("screen.friendsmod.invite", "Invite Friends");
        translations.put("screen.friendsmod.select_friends", "Select Friends");
        translations.put("screen.friendsmod.chat", "Chat");
        translations.put("screen.friendsmod.profile", "Profile");
        translations.put("screen.friendsmod.settings", "Settings");
        translations.put("screen.friendsmod.edit_profile", "Edit Profile");
        
        translations.put("text.friendsmod.username", "Username");
        translations.put("text.friendsmod.friend_uid", "Friend UID");
        translations.put("text.friendsmod.message", "Message");
        translations.put("text.friendsmod.search", "Search");
        translations.put("text.friendsmod.status", "Status");
        translations.put("text.friendsmod.online", "Online");
        translations.put("text.friendsmod.offline", "Offline");
        translations.put("text.friendsmod.in_menu", "In Menu");
        translations.put("text.friendsmod.in_world", "In World");
        translations.put("text.friendsmod.distance", "Distance");
        
        translations.put("button.friendsmod.login", "Login");
        translations.put("button.friendsmod.skip", "Skip");
        translations.put("button.friendsmod.add", "Add");
        translations.put("button.friendsmod.send_request", "Send Request");
        translations.put("button.friendsmod.accept", "Accept");
        translations.put("button.friendsmod.reject", "Reject");
        translations.put("button.friendsmod.remove", "Remove");
        translations.put("button.friendsmod.block", "Block");
        translations.put("button.friendsmod.unblock", "Unblock");
        translations.put("button.friendsmod.invite_friends", "Invite Friends");
        translations.put("button.friendsmod.invite_selected", "Invite Selected");
        translations.put("button.friendsmod.send", "Send");
        translations.put("button.friendsmod.voice_chat", "Voice Chat");
        translations.put("button.friendsmod.change_skin", "Change Skin");
        translations.put("button.friendsmod.copy_uid", "Copy UID");
        translations.put("button.friendsmod.dark_mode", "Dark Mode");
        translations.put("button.friendsmod.light_mode", "Light Mode");
        translations.put("button.friendsmod.blur", "Blur Background");
        translations.put("button.friendsmod.disable_blur", "Disable Blur");
        translations.put("button.friendsmod.enable_blur", "Enable Blur");
        translations.put("button.friendsmod.sound_effects", "Sound Effects");
        translations.put("button.friendsmod.disable_sound", "Disable Sound");
        translations.put("button.friendsmod.enable_sound", "Enable Sound");
        translations.put("button.friendsmod.enable_voice", "Enable Voice");
        translations.put("button.friendsmod.disable_voice", "Disable Voice");
        translations.put("button.friendsmod.edit_profile", "Edit Profile");
        
        translations.put("message.friendsmod.uid_copied", "UID copied to clipboard!");
        translations.put("message.friendsmod.friend_added", "Friend added successfully!");
        translations.put("message.friendsmod.request_sent", "Friend request sent!");
        translations.put("message.friendsmod.request_accepted", "Friend request accepted!");
        translations.put("message.friendsmod.request_rejected", "Friend request rejected!");
        translations.put("message.friendsmod.friend_removed", "Friend removed!");
        translations.put("message.friendsmod.friend_blocked", "Friend blocked!");
        translations.put("message.friendsmod.friend_unblocked", "Friend unblocked!");
        translations.put("message.friendsmod.invites_sent", "Invites sent to selected friends!");
        translations.put("message.friendsmod.welcome", "Welcome to Friends Mod!");
        translations.put("message.friendsmod.joined_world", "Joined your world!");
        translations.put("message.friendsmod.cooldown", "Invite cooldown active!");
        
        // Load other languages if available
        // For now, we only have English
    }
    
    public static String translate(String key) {
        String translation = translations.get(key);
        if (translation == null) {
            return key; // Return key if no translation found
        }
        return translation;
    }
    
    public static String getCurrentLanguage() {
        return currentLanguage;
    }
    
    public static void setCurrentLanguage(String language) {
        currentLanguage = language;
        loadTranslations();
    }
    
    public static boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }
}