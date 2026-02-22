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

public class SettingsScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/settings.png");
    private static final Identifier DARK_MODE_ICON = new Identifier("friendsmod", "textures/gui/dark_mode.png");
    private static final Identifier LIGHT_MODE_ICON = new Identifier("friendsmod", "textures/gui/light_mode.png");
    
    private ButtonWidget darkModeButton;
    private ButtonWidget blurButton;
    private ButtonWidget soundButton;
    private ButtonWidget cancelButton;
    private boolean darkMode = false;
    private boolean blurEnabled = true;
    private boolean soundEnabled = true;
    
    protected SettingsScreen() {
        super(new TranslatableText("screen.friendsmod.settings"));
    }
    
    @Override
    public void init() {
        // Dark mode button
        darkModeButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 - 60,
            200, 40,
            new TranslatableText("button.friendsmod.dark_mode"),
            button -> toggleDarkMode()
        );
        
        // Blur button
        blurButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 10,
            200, 40,
            new TranslatableText("button.friendsmod.blur"),
            button -> toggleBlur()
        );
        
        // Sound button
        soundButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 80,
            200, 40,
            new TranslatableText("button.friendsmod.sound_effects"),
            button -> toggleSound()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 50,
            height - 80,
            100, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new FriendsGui())
        );
        
        buttons.add(darkModeButton);
        buttons.add(blurButton);
        buttons.add(soundButton);
        buttons.add(cancelButton);
    }
    
    private void toggleDarkMode() {
        darkMode = !darkMode;
        darkModeButton.setMessage(new TranslatableText(darkMode ? "button.friendsmod.light_mode" : "button.friendsmod.dark_mode"));
        // Apply dark mode settings
    }
    
    private void toggleBlur() {
        blurEnabled = !blurEnabled;
        blurButton.setMessage(new TranslatableText(blurEnabled ? "button.friendsmod.disable_blur" : "button.friendsmod.enable_blur"));
        // Apply blur settings
    }
    
    private void toggleSound() {
        soundEnabled = !soundEnabled;
        soundButton.setMessage(new TranslatableText(soundEnabled ? "button.friendsmod.disable_sound" : "button.friendsmod.enable_sound"));
        // Apply sound settings
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        
        // Draw settings
        drawString(matrices, textRenderer, "UI Settings:", width / 2 - 150, height / 2 - 90, 0xAAAAAA);
        
        darkModeButton.render(matrices, mouseX, mouseY, delta);
        blurButton.render(matrices, mouseX, mouseY, delta);
        soundButton.render(matrices, mouseX, mouseY, delta);
        cancelButton.render(matrices, mouseX, mouseY, delta);
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.8F);
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
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