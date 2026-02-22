package com.friendsmod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class FriendsGui extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/friends_gui.png");
    private static final Identifier SKIN_TEXTURE = new Identifier("friendsmod", "textures/entity/steve.png");
    
    private ButtonWidget addFriendButton;
    private ButtonWidget changeSkinButton;
    private ButtonWidget copyUidButton;
    private TextFieldWidget searchField;
    private FriendsListWidget friendsList;
    
    protected FriendsGui() {
        super(new TranslatableText("screen.friendsmod.friends"));
    }
    
    @Override
    public void init() {
        // Add buttons
        addFriendButton = new ButtonWidget(
            width - 120, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.add_friend"),
            button -> minecraft.openScreen(new AddFriendScreen())
        );
        
        changeSkinButton = new ButtonWidget(
            30, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.change_skin"),
            button -> handleSkinChange()
        );
        
        copyUidButton = new ButtonWidget(
            140, height - 40,
            100, 20,
            new TranslatableText("button.friendsmod.copy_uid"),
            button -> copyUidToClipboard()
        );
        
        // Add search field
        searchField = new TextFieldWidget(
            textRenderer,
            30, 80,
            200, 20,
            new TranslatableText("text.friendsmod.search")
        );
        searchField.setMaxLength(20);
        children.add(searchField);
        
        // Add friends list
        friendsList = new FriendsListWidget(minecraft, this, textRenderer);
        children.add(friendsList);
        
        buttons.add(addFriendButton);
        buttons.add(changeSkinButton);
        buttons.add(copyUidButton);
    }
    
    private void handleSkinChange() {
        // Open skin selection screen (simplified)
        PlayerDataManager.setSkin("new_skin");
        minecraft.openScreen(new FriendsGui());
    }
    
    private void copyUidToClipboard() {
        String uid = PlayerDataManager.getPlayerData().getUid();
        minecraft.keyboard.setClipboard(uid);
        minecraft.inGameHud.setOverlayMessage(
            new TranslatableText("message.friendsmod.uid_copied"),
            false
        );
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw glass background
        renderGlassBackground(matrices);
        
        // Draw title
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        
        // Draw player info
        drawPlayerInfo(matrices);
        
        // Draw search label
        drawString(matrices, textRenderer, "Search:", 30, 65, 0xAAAAAA);
        
        searchField.render(matrices, mouseX, mouseY, delta);
        friendsList.render(matrices, mouseX, mouseY, delta);
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    private void renderGlassBackground(MatrixStack matrices) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.8F);
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrices, 0, 0, 0, 0, width, height, 256, 256);
    }
    
    private void drawPlayerInfo(MatrixStack matrices) {
        PlayerData playerData = PlayerDataManager.getPlayerData();
        
        // Draw skin preview
        Texture skinTexture = SkinManager.getSkinTexture(playerData.getSkin());
        minecraft.getTextureManager().bindTexture(skinTexture);
        blit(matrices, 30, 120, 0, 0, 64, 64, 64, 64);
        
        // Draw username and UID
        drawString(matrices, textRenderer, "Username:", 110, 130, 0xAAAAAA);
        drawString(matrices, textRenderer, playerData.getUsername(), 110, 140, 0xFFFFFF);
        
        drawString(matrices, textRenderer, "UID:", 110, 160, 0xAAAAAA);
        drawString(matrices, textRenderer, playerData.getUid(), 110, 170, 0xCCCCCC);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        searchField.mouseClicked(mouseX, mouseY, button);
        friendsList.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
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