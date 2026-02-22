package com.friendsmod.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class AddFriendScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/add_friend.png");
    
    private TextFieldWidget uidField;
    private ButtonWidget addButton;
    private ButtonWidget cancelButton;
    
    protected AddFriendScreen() {
        super(new TranslatableText("screen.friendsmod.add_friend"));
    }
    
    @Override
    public void init() {
        // UID field
        uidField = new TextFieldWidget(
            textRenderer,
            width / 2 - 150,
            height / 2 - 40,
            300, 30,
            new TranslatableText("text.friendsmod.friend_uid")
        );
        uidField.setMaxLength(36);
        children.add(uidField);
        
        // Add button
        addButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 20,
            200, 40,
            new TranslatableText("button.friendsmod.add"),
            button -> handleAdd()
        );
        
        // Cancel button
        cancelButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 70,
            200, 40,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(new FriendsGui())
        );
        
        buttons.add(addButton);
        buttons.add(cancelButton);
        
        uidField.setFocus(true);
    }
    
    private void handleAdd() {
        String uid = uidField.getText().trim();
        if (!uid.isEmpty()) {
            // Simulate friend lookup (in real implementation, this would connect to server)
            String friendUsername = "Friend" + uid.substring(0, 4);
            String friendSkin = "default";
            
            com.friendsmod.PlayerDataManager.Friend friend = new com.friendsmod.PlayerDataManager.Friend(
                friendUsername, uid, friendSkin
            );
            com.friendsmod.PlayerDataManager.addFriend(friend);
            
            minecraft.inGameHud.setOverlayMessage(
                new TranslatableText("message.friendsmod.friend_added"),
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
        drawCenteredString(matrices, textRenderer, title, width / 2, 50, 0xFFFFFF);
        
        // Draw instructions
        drawCenteredString(matrices, textRenderer, "Enter your friend's UID:", width / 2, 100, 0xAAAAAA);
        
        // Draw UID label
        drawString(matrices, textRenderer, "UID:", width / 2 - 150, height / 2 - 55, 0xAAAAAA);
        
        uidField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) { // Enter key
            handleAdd();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}