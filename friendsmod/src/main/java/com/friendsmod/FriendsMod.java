package com.friendsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FriendsMod implements ClientModInitializer {
    
    public static MinecraftClient minecraft;
    public static KeyBinding friendsKeyBinding;
    public static VoiceChatManager voiceChatManager;
    public static NetworkManager networkManager;
    
    @Override
    public void onInitializeClient() {
        minecraft = MinecraftClient.getInstance();
        
        // Initialize managers
        voiceChatManager = new VoiceChatManager();
        networkManager = new NetworkManager();
        
        // Register key binding
        friendsKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.friendsmod.friends", 
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.friendsmod"
        ));
        
        // Add Friends button to main menu
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.currentScreen instanceof MainMenuScreen) {
                MainMenuScreen mainMenu = (MainMenuScreen) minecraft.currentScreen;
                mainMenu.addButton(new TexturedButtonWidget(
                    mainMenu.width / 2 - 100,
                    mainMenu.height / 4 + 120 + 48,
                    200, 20,
                    new TranslatableText("button.friendsmod.friends"),
                    button -> minecraft.openScreen(new LoginScreen())
                ));
            }
        });
        
        // Handle login connection
        ClientLoginConnectionEvents.LOGIN.register((handler, sender, client) -> {
            PlayerDataManager.init();
            FriendManager.init();
        });
        
        // Handle world entry
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (minecraft.world != null && minecraft.world.getLevelProperties().isSingleplayer()) {
                minecraft.execute(() -> {
                    minecraft.openScreen(new InviteFriendsScreen());
                });
            }
        });
        
        // Handle friend connections
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            networkManager.handleFriendConnection(handler.getPlayer());
        });
    }
    
    public static void openFriendsGui() {
        minecraft.openScreen(new FriendsGui());
    }
    
    public static void toggleVoiceChat() {
        voiceChatManager.toggle();
    }
}