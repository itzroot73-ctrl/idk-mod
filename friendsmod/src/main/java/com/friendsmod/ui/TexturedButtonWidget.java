package com.friendsmod.ui;

import net.minecraft.client.gui.widget.ButtonWidget;

public class TexturedButtonWidget extends ButtonWidget {
    
    private final int u;
    private final int v;
    private final int textureWidth;
    private final int textureHeight;
    private boolean isHovered = false;
    private long hoverStartTime;
    
    public TexturedButtonWidget(int x, int y, int width, int height, String text, PressAction onPress) {
        super(x, y, width, height, text, onPress);
        this.u = 0;
        this.v = 0;
        this.textureWidth = 200;
        this.textureHeight = 20;
    }
    
    public TexturedButtonWidget(int x, int y, int width, int height, String text, int u, int v, int textureWidth, int textureHeight, PressAction onPress) {
        super(x, y, width, height, text, onPress);
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
    
    @Override
    public void renderButton(int mouseX, int mouseY, float delta) {
        isHovered = isHovered(mouseX, mouseY);
        
        if (isHovered) {
            if (hoverStartTime == 0) {
                hoverStartTime = System.currentTimeMillis();
            }
            // Add hover glow effect
            fill(x - 2, y - 2, x + width + 2, y + height + 2, 0x88FFFFFF);
        } else {
            hoverStartTime = 0;
        }
        
        super.renderButton(mouseX, mouseY, delta);
    }
    
    public boolean isHovered() {
        return isHovered;
    }
    
    public long getHoverDuration() {
        if (hoverStartTime == 0) return 0;
        return System.currentTimeMillis() - hoverStartTime;
    }
}