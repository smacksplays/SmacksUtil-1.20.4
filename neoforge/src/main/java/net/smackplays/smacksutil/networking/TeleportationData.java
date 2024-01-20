package net.smackplays.smacksutil.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record TeleportationData(ResourceKey<?> levelKey, Vec3 pos, float xRot, float yRot) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "teleportation_data");

    public TeleportationData(final FriendlyByteBuf buffer) {
        this(buffer.readRegistryKey(), buffer.readVec3(), buffer.readFloat(), buffer.readFloat());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeResourceKey(levelKey);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}