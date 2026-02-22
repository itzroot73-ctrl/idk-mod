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

public class LoginScreen extends Screen {
    
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("friendsmod", "textures/gui/login_background.png");
    private static final Identifier LOGO_TEXTURE = new Identifier("friendsmod", "textures/gui/logo.png");
    
    private String username = "";
    private TextFieldWidget usernameField;
    private ButtonWidget loginButton;
    private ButtonWidget skipButton;
    
    protected LoginScreen() {
        super(new TranslatableText("screen.friendsmod.login"));
    }
    
    @Override
    public void init() {
        // Username field
        usernameField = new TextFieldWidget(
            textRenderer,
            width / 2 - 150,
            height / 2 + 40,
            300, 30,
            new TranslatableText("text.friendsmod.username")
        );
        usernameField.setMaxLength(16);
        usernameField.setText(com.friendsmod.PlayerDataManager.getPlayerData().getUsername());
        children.add(usernameField);
        
        // Login button
        loginButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 80,
            200, 40,
            new TranslatableText("button.friendsmod.login"),
            button -> handleLogin()
        );
        
        // Skip button
        skipButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 130,
            200, 40,
            new TranslatableText("button.friendsmod.skip"),
            button -> skipLogin()
        );
        
        buttons.add(loginButton);
        buttons.add(skipButton);
        
        usernameField.setFocus(true);
    }
    
    private void handleLogin() {
        String newUsername = usernameField.getText().trim();
        if (!newUsername.isEmpty()) {
            com.friendsmod.PlayerDataManager.setUsername(newUsername);
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    private void skipLogin() {
        minecraft.openScreen(new FriendsGui());
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
        drawCenteredString(matrices, textRenderer, "Enter your custom username", width / 2, 220, 0xAAAAAA);
        
        // Draw username label
        drawString(matrices, textRenderer, "Username:", width / 2 - 150, height / 2 + 25, 0xAAAAAA);
        
        usernameField.render(matrices, mouseX, mouseY, delta);
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
            handleLogin();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}