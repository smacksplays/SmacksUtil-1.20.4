package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Set;

public class C2STeleportationPacketHandler {
    private static final C2STeleportationPacketHandler INSTANCE = new C2STeleportationPacketHandler();

    public static C2STeleportationPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2STeleportationPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.level().isPresent() && context.player().isPresent()) {
                ServerLevel level = (ServerLevel) context.level().get();
                Player player = context.player().get();
                ResourceKey<?> tempKey = data.levelKey();
                ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
                Vec3 pos = data.pos();
                float xRot = data.xRot();
                float yRot = data.yRot();

                MinecraftServer server = level.getServer();
                ServerLevel serverLevel = server.getLevel(levelKey);
                //player.teleportTo(pos.x, pos.y, pos.z);
                if (serverLevel != null) {
                    player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
                }
            }
        });
    }
}
