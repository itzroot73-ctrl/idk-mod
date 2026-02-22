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

public class ChatScreen extends Screen {
    
    private static final Identifier CHAT_TEXTURE = new Identifier("friendsmod", "textures/gui/chat.png");
    
    private TextFieldWidget messageField;
    private ButtonWidget sendButton;
    private ButtonWidget voiceButton;
    private ButtonWidget closeButton;
    private String selectedFriendUid = "";
    
    protected ChatScreen(String friendUid) {
        super(new TranslatableText("screen.friendsmod.chat"));
        this.selectedFriendUid = friendUid;
    }
    
    @Override
    public void init() {
        // Message field
        messageField = new TextFieldWidget(
            textRenderer,
            width / 2 - 150,
            height / 2 + 60,
            300, 30,
            new TranslatableText("text.friendsmod.message")
        );
        children.add(messageField);
        
        // Send button
        sendButton = new ButtonWidget(
            width / 2 + 80,
            height / 2 + 60,
            60, 30,
            new TranslatableText("button.friendsmod.send"),
            button -> handleSend()
        );
        
        // Voice button
        voiceButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 100,
            200, 30,
            new TranslatableText("button.friendsmod.voice_chat"),
            button -> handleVoice()
        );
        
        // Close button
        closeButton = new ButtonWidget(
            width / 2 - 50,
            height / 2 + 140,
            100, 30,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(null)
        );
        
        buttons.add(sendButton);
        buttons.add(voiceButton);
        buttons.add(closeButton);
        
        messageField.setFocus(true);
    }
    
    private void handleSend() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // Send message to friend
            com.friendsmod.NetworkManager.sendChatMessage(selectedFriendUid, message);
            
            // Add message to chat history
            addChatMessage("You", message);
            
            messageField.setText("");
        }
    }
    
    private void handleVoice() {
        com.friendsmod.FriendsMod.toggleVoiceChat();
        if (com.friendsmod.FriendsMod.voiceChatManager.isEnabled()) {
            voiceButton.setMessage(new TranslatableText("button.friendsmod.disable_voice"));
        } else {
            voiceButton.setMessage(new TranslatableText("button.friendsmod.enable_voice"));
        }
    }
    
    private void addChatMessage(String sender, String message) {
        // Add message to chat history display
        // This would require a chat history system
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 50, 0xFFFFFF);
        
        // Draw friend info
        com.friendsmod.PlayerDataManager.Friend friend = com.friendsmod.PlayerDataManager.getFriendByUid(selectedFriendUid);
        if (friend != null) {
            drawString(matrices, textRenderer, "Chatting with:", width / 2 - 150, height / 2 - 50, 0xAAAAAA);
            drawString(matrices, textRenderer, friend.getUsername(), width / 2 - 150, height / 2 - 30, 0x00FF00);
            
            String status = friend.isOnline() ? "Online" : "Offline";
            drawString(matrices, textRenderer, status, width / 2 + 50, height / 2 - 30, friend.isOnline() ? 0x00FF00 : 0xFF0000);
        }
        
        // Draw message field label
        drawString(matrices, textRenderer, "Message:", width / 2 - 150, height / 2 + 45, 0xAAAAAA);
        
        messageField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        minecraft.getTextureManager().bindTexture(CHAT_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) { // Enter key
            handleSend();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}