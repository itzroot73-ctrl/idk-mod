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

public class ManageFriendsScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/manage_friends.png");
    
    private ButtonWidget removeButton;
    private ButtonWidget blockButton;
    private ButtonWidget unblockButton;
    private ButtonWidget cancelButton;
    private FriendsListWidget friendsList;
    
    protected ManageFriendsScreen() {
        super(new TranslatableText("screen.friendsmod.manage_friends"));
    }
    
    @Override
    public void init() {
        // Friends list
        friendsList = new FriendsListWidget(minecraft, this, textRenderer);
        children.add(friendsList);
        
        // Remove button
        removeButton = new ButtonWidget(
            width - 120, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.remove"),
            button -> handleRemove()
        );
        
        // Block button
        blockButton = new ButtonWidget(
            20, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.block"),
            button -> handleBlock()
        );
        
        // Unblock button
        unblockButton = new ButtonWidget(
            140, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.unblock"),
            button -> handleUnblock()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 50, height - 40,
            100, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new FriendsGui())
        );
        
        buttons.add(removeButton);
        buttons.add(blockButton);
        buttons.add(unblockButton);
        buttons.add(cancelButton);
    }
    
    private void handleRemove() {
        com.friendsmod.PlayerDataManager.Friend friend = friendsList.getSelectedFriend();
        if (friend != null) {
            com.friendsmod.PlayerDataManager.removeFriend(friend.getUid());
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.friend_removed"),
                false
            );
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    private void handleBlock() {
        com.friendsmod.PlayerDataManager.Friend friend = friendsList.getSelectedFriend();
        if (friend != null) {
            com.friendsmod.PlayerDataManager.blockFriend(friend.getUid());
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.friend_blocked"),
                false
            );
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    private void handleUnblock() {
        com.friendsmod.PlayerDataManager.Friend friend = friendsList.getSelectedFriend();
        if (friend != null) {
            com.friendsmod.PlayerDataManager.unblockFriend(friend.getUid());
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.friend_unblocked"),
                false
            );
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        
        // Draw instructions
        drawCenteredString(matrices, textRenderer, "Select a friend to manage:", width / 2, 50, 0xAAAAAA);
        
        friendsList.render(matrices, mouseX, mouseY, delta);
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