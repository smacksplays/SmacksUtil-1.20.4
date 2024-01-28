package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Set;

public class C2STeleportationPacket {
    private final ResourceKey<Level> levelKey;
    private final Vec3 pos;
    private final float xRot;
    private final float yRot;

    public C2STeleportationPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        this.levelKey = levelKey;
        this.pos = pos;
        this.xRot = xRot;
        this.yRot = yRot;
    }

    public C2STeleportationPacket(FriendlyByteBuf buffer) {
        var tempKey = buffer.readRegistryKey();
        levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
        pos = buffer.readVec3();
        xRot = buffer.readFloat();
        yRot = buffer.readFloat();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceKey(levelKey);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null) return;
        Level level = player.level();

        MinecraftServer server = level.getServer();
        ServerLevel serverLevel = null;
        if (server != null) {
            serverLevel = server.getLevel(levelKey);
        }
        //player.teleportTo(pos.x, pos.y, pos.z);
        if (serverLevel != null) {
            player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
        }
    }
}