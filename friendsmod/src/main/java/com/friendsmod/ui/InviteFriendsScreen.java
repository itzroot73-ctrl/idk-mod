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

public class InviteFriendsScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/invite_background.png");
    private static final Identifier LOGO_TEXTURE = new Identifier("friendsmod", "textures/gui/logo.png");
    
    private ButtonWidget inviteButton;
    private ButtonWidget cancelButton;
    
    protected InviteFriendsScreen() {
        super(new TranslatableText("screen.friendsmod.invite"));
    }
    
    @Override
    public void init() {
        // Invite button
        inviteButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 80,
            200, 40,
            new TranslatableText("button.friendsmod.invite_friends"),
            button -> handleInvite()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 130,
            200, 40,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(null)
        );
        
        buttons.add(inviteButton);
        buttons.add(cancelButton);
    }
    
    private void handleInvite() {
        // Show friend selection screen
        minecraft.openScreen(new SelectFriendsScreen());
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw logo
        minecraft.getTextureManager().bindTexture(LOGO_TEXTURE);
        blit(matrices, width / 2 - 100, 100, 0, 0, 200, 80, 200, 80);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 200, 0xFFFFFF);
        
        // Draw subtitle
        drawCenteredString(matrices, textRenderer, "Would you like to invite friends to your world?", width / 2, 220, 0xAAAAAA);
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // Escape key
            minecraft.openScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}