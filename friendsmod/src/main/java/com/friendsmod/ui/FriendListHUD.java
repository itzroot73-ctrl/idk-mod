package com.friendsmod.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class FriendListHUD extends Screen {
    
    private static final Identifier HUD_TEXTURE = new Identifier("friendsmod", "textures/gui/hud.png");
    private static final int HUD_WIDTH = 200;
    private static final int HUD_HEIGHT = 300;
    
    private boolean visible = false;
    private long lastUpdate = 0;
    
    public FriendListHUD() {
        super(new TranslatableText("screen.friendsmod.friend_list"));
    }
    
    public void toggle() {
        visible = !visible;
        if (visible) {
            updateFriendList();
        }
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    private void updateFriendList() {
        // Update friend list every 5 seconds
        if (System.currentTimeMillis() - lastUpdate > 5000) {
            lastUpdate = System.currentTimeMillis();
            // Update friend statuses and positions
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!visible) return;
        
        MinecraftClient minecraft = MinecraftClient.getInstance();
        
        // Draw HUD background
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.8F);
        minecraft.getTextureManager().bindTexture(HUD_TEXTURE);
        blit(matrices, 10, 10, 0, 0, HUD_WIDTH, HUD_HEIGHT, 256, 256);
        
        // Draw title
        drawString(matrices, minecraft.textRenderer, "Friends Online:", 20, 20, 0xFFFFFF);
        
        // Draw friend list
        int y = 40;
        for (com.friendsmod.PlayerDataManager.Friend friend : com.friendsmod.PlayerDataManager.getFriends()) {
            if (friend.isOnline()) {
                String status = friend.getLastWorld().isEmpty() ? "In Menu" : "In World: " + friend.getLastWorld();
                drawString(matrices, minecraft.textRenderer, friend.getUsername(), 20, y, 0x00FF00);
                drawString(matrices, minecraft.textRenderer, status, 20, y + 10, 0xAAAAAA);
                y += 30;
            }
        }
        
        // Draw distance to each friend (if in same world)
        if (minecraft.world != null) {
            for (com.friendsmod.PlayerDataManager.Friend friend : com.friendsmod.PlayerDataManager.getFriends()) {
                if (friend.isOnline() && minecraft.world.getRegistryKey().getValue().equals(friend.getLastWorld())) {
                    // Calculate distance (simplified)
                    double distance = Math.random() * 100; // Replace with actual distance calculation
                    drawString(matrices, minecraft.textRenderer, String.format("Distance: %.1f blocks", distance), 20, y, 0x0000FF);
                    y += 20;
                }
            }
        }
    }
    
    public void renderFriendStatus(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        
        // Render friend status indicators
        for (com.friendsmod.PlayerDataManager.Friend friend : com.friendsmod.PlayerDataManager.getFriends()) {
            if (friend.isOnline()) {
                // Draw status indicator near friend's position
                // This would require world coordinates and rendering on screen
            }
        }
    }
}