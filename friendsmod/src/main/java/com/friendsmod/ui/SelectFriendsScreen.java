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

import java.util.List;

public class SelectFriendsScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/select_friends.png");
    
    private List<com.friendsmod.PlayerDataManager.Friend> friends;
    private ButtonWidget inviteButton;
    private ButtonWidget cancelButton;
    private FriendListWidget friendList;
    
    protected SelectFriendsScreen() {
        super(new TranslatableText("screen.friendsmod.select_friends"));
        this.friends = com.friendsmod.PlayerDataManager.getFriends();
    }
    
    @Override
    public void init() {
        // Friend list
        friendList = new FriendListWidget(minecraft, this, textRenderer, friends);
        children.add(friendList);
        
        // Invite button
        inviteButton = new ButtonWidget(
            width - 120, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.invite_selected"),
            button -> handleInvite()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            20, height - 40,
            100, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new InviteFriendsScreen())
        );
        
        buttons.add(inviteButton);
        buttons.add(cancelButton);
    }
    
    private void handleInvite() {
        List<com.friendsmod.PlayerDataManager.Friend> selectedFriends = friendList.getSelectedFriends();
        if (!selectedFriends.isEmpty()) {
            // Send invites to selected friends
            for (com.friendsmod.PlayerDataManager.Friend friend : selectedFriends) {
                com.friendsmod.NetworkManager.sendInvite(friend.getUid());
            }
            
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.invites_sent"),
                false
            );
            minecraft.openScreen(null);
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        
        // Draw instructions
        drawCenteredString(matrices, textRenderer, "Select friends to invite:", width / 2, 50, 0xAAAAAA);
        
        friendList.render(matrices, mouseX, mouseY, delta);
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
            minecraft.openScreen(new InviteFriendsScreen());
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}