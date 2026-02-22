package com.friendsmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Texture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkinManager {
    
    private static final Map<Identifier, Texture> skinCache = new HashMap<>();
    private static final Identifier DEFAULT_SKIN = new Identifier("friendsmod", "textures/entity/steve.png");
    
    public static Texture getSkinTexture(String username) {
        Identifier skinId = new Identifier("friendsmod", "skins/" + username + ".png");
        
        if (skinCache.containsKey(skinId)) {
            return skinCache.get(skinId);
        }
        
        // Try to load from cache or download (simplified)
        Texture texture = loadSkinFromResource(skinId);
        if (texture != null) {
            skinCache.put(skinId, texture);
        } else {
            // Fallback to default skin
            texture = loadSkinFromResource(DEFAULT_SKIN);
            if (texture != null) {
                skinCache.put(DEFAULT_SKIN, texture);
            }
        }
        
        return texture != null ? texture : MinecraftClient.getInstance().getTextureManager().getTexture(DEFAULT_SKIN);
    }
    
    @Nullable
    private static Texture loadSkinFromResource(Identifier id) {
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();
        
        try {
            Resource resource = resourceManager.getResource(id);
            if (resource != null) {
                return new Texture(resource.getInputStream());
            }
        } catch (IOException e) {
            // Skin not found, return null
        }
        
        return null;
    }
    
    public static void clearCache() {
        skinCache.clear();
    }
}