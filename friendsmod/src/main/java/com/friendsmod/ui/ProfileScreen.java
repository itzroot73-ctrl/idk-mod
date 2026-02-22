package com.friendsmod.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ProfileScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/profile.png");
    private static final Identifier SKIN_TEXTURE = new Identifier("friendsmod", "textures/entity/steve.png");
    
    private ButtonWidget editButton;
    private ButtonWidget cancelButton;
    
    protected ProfileScreen() {
        super(new TranslatableText("screen.friendsmod.profile"));
    }
    
    @Override
    public void init() {
        // Edit button
        editButton = new ButtonWidget(
            width / 2 - 100,
            height - 80,
            200, 40,
            new TranslatableText("button.friendsmod.edit_profile"),
            button -> handleEdit()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 50,
            height - 40,
            100, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new FriendsGui())
        );
        
        buttons.add(editButton);
        buttons.add(cancelButton);
    }
    
    private void handleEdit() {
        minecraft.openScreen(new EditProfileScreen());
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        
        // Draw player info
        drawPlayerInfo(matrices);
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.8F);
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
    }
    
    private void drawPlayerInfo(MatrixStack matrices) {
        com.friendsmod.PlayerDataManager.PlayerData playerData = com.friendsmod.PlayerDataManager.getPlayerData();
        
        // Draw skin preview
        Texture skinTexture = com.friendsmod.SkinManager.getSkinTexture(playerData.getSkin());
        minecraft.getTextureManager().bindTexture(skinTexture);
        blit(matrices, width / 2 - 50, 120, 0, 0, 64, 64, 64, 64);
        
        // Draw username
        drawCenteredString(matrices, textRenderer, "Username:", width / 2, 200, 0xAAAAAA);
        drawCenteredString(matrices, textRenderer, playerData.getUsername(), width / 2, 220, 0xFFFFFF);
        
        // Draw UID
        drawCenteredString(matrices, textRenderer, "UID:", width / 2, 240, 0xAAAAAA);
        drawCenteredString(matrices, textRenderer, playerData.getUid(), width / 2, 260, 0xCCCCCC);
        
        // Draw status message
        drawCenteredString(matrices, textRenderer, "Status:", width / 2, 280, 0xAAAAAA);
        drawCenteredString(matrices, textRenderer, playerData.getStatusMessage(), width / 2, 300, 0x00FF00);
        
        // Draw online status
        String onlineStatus = playerData.isOnline() ? "Online" : "Offline";
        int onlineColor = playerData.isOnline() ? 0x00FF00 : 0xFF0000;
        drawCenteredString(matrices, textRenderer, "Status:", width / 2, 320, 0xAAAAAA);
        drawCenteredString(matrices, textRenderer, onlineStatus, width / 2, 340, onlineColor);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // Escape key
            minecraft.openScreen(new FriendsGui());
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}