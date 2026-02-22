package com.friendsmod;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class LoginScreen extends Screen {
    
    private String username = "";
    private ButtonWidget loginButton;
    private TextFieldWidget usernameField;
    
    protected LoginScreen() {
        super(new TranslatableText("screen.friendsmod.login"));
    }
    
    @Override
    public void init() {
        usernameField = new TextFieldWidget(
            textRenderer,
            width / 2 - 100,
            height / 2 - 20,
            200, 20,
            new TranslatableText("text.friendsmod.username")
        );
        usernameField.setMaxLength(16);
        usernameField.setText(PlayerDataManager.getPlayerData().getUsername());
        children.add(usernameField);
        
        loginButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 20,
            200, 20,
            new TranslatableText("button.friendsmod.login"),
            button -> handleLogin()
        );
        
        ButtonWidget cancelButton = new ButtonWidget(
            width / 2 - 100,
            height / 2 + 50,
            200, 20,
            ScreenTexts.CANCEL,
            button -> minecraft.openScreen(null)
        );
        
        buttons.add(loginButton);
        buttons.add(cancelButton);
        
        usernameField.setFocus(true);
    }
    
    private void handleLogin() {
        String newUsername = usernameField.getText().trim();
        if (!newUsername.isEmpty()) {
            PlayerDataManager.setUsername(newUsername);
            minecraft.openScreen(new FriendsGui());
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredString(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
        drawString(matrices, textRenderer, "Enter your username:", width / 2 - 100, height / 2 - 50, 0xAAAAAA);
        
        usernameField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
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