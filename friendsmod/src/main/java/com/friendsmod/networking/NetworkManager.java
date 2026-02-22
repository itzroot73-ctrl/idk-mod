package com.friendsmod.networking;

import com.friendsmod.PlayerDataManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    
    private static final Map<String, ServerPlayerEntity> friendConnections = new HashMap<>();
    private static final Map<String, ServerWorld> friendWorlds = new HashMap<>();
    
    public static void handleFriendConnection(ServerPlayerEntity player) {
        String uid = getUIDFromPlayer(player);
        if (uid != null) {
            friendConnections.put(uid, player);
            friendWorlds.put(uid, player.getServerWorld());
            
            // Send welcome message
            player.sendMessage(new TranslatableText("message.friendsmod.welcome"), true);
        }
    }
    
    public static void sendInvite(String friendUid) {
        ServerPlayerEntity friend = friendConnections.get(friendUid);
        if (friend != null) {
            // Send invite packet to friend
            PacketByteBuf packet = new PacketByteBuf(friend.getPacketBuffer());
            packet.writeBoolean(true); // Invite flag
            packet.writeString("INVITE"); // Invite type
            packet.writeString("Your friend invited you to their world!"); // Message
            
            // In a real implementation, this would send the packet to the friend
            System.out.println("Invite sent to friend: " + friendUid);
        }
    }
    
    public static void handleInviteResponse(String friendUid, boolean accepted) {
        if (accepted) {
            // Connect friend to the world
            ServerPlayerEntity friend = friendConnections.get(friendUid);
            if (friend != null) {
                ServerWorld world = friendWorlds.get(friendUid);
                if (world != null) {
                    // Teleport friend to the host world
                    friend.teleport(world, world.getSpawnPos().getX(), world.getSpawnPos().getY(), world.getSpawnPos().getZ(), 0.0F, 0.0F);
                    friend.sendMessage(new TranslatableText("message.friendsmod.joined_world"), true);
                }
            }
        }
    }
    
    private static String getUIDFromPlayer(ServerPlayerEntity player) {
        // In a real implementation, this would get the UID from player data
        // For now, we simulate it
        return "UID_" + player.getUuidAsString().substring(0, 8);
    }
    
    public static void cleanup() {
        friendConnections.clear();
        friendWorlds.clear();
    }
}