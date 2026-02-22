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

public class FriendRequestsScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/friend_requests.png");
    
    private ButtonWidget acceptButton;
    private ButtonWidget rejectButton;
    private ButtonWidget cancelButton;
    private FriendRequestListWidget requestList;
    
    protected FriendRequestsScreen() {
        super(new TranslatableText("screen.friendsmod.friend_requests"));
    }
    
    @Override
    public void init() {
        // Friend request list
        requestList = new FriendRequestListWidget(minecraft, this, textRenderer);
        children.add(requestList);
        
        // Accept button
        acceptButton = new ButtonWidget(
            width - 120, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.accept"),
            button -> handleAccept()
        );
        
        // Reject button
        rejectButton = new ButtonWidget(
            20, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.reject"),
            button -> handleReject()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 50, height - 40,
            100, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new FriendsGui())
        );
        
        buttons.add(acceptButton);
        buttons.add(rejectButton);
        buttons.add(cancelButton);
    }
    
    private void handleAccept() {
        com.friendsmod.PlayerDataManager.FriendRequest request = requestList.getSelectedRequest();
        if (request != null) {
            // Add friend
            com.friendsmod.PlayerDataManager.Friend friend = new com.friendsmod.PlayerDataManager.Friend(
                request.getUsername(), request.getUid(), request.getSkin()
            );
            com.friendsmod.PlayerDataManager.addFriend(friend);
            
            // Remove request
            com.friendsmod.PlayerDataManager.removeFriendRequest(request.getUid());
            
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.request_accepted"),
                false
            );
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    private void handleReject() {
        com.friendsmod.PlayerDataManager.FriendRequest request = requestList.getSelectedRequest();
        if (request != null) {
            com.friendsmod.PlayerDataManager.removeFriendRequest(request.getUid());
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.request_rejected"),
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
        drawCenteredString(matrices, textRenderer, "Select a friend request to manage:", width / 2, 50, 0xAAAAAA);
        
        requestList.render(matrices, mouseX, mouseY, delta);
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