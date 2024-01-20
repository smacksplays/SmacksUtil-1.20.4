package net.smackplays.smacksutil.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public record TeleportationNBTData(ResourceKey<?> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "teleportation_nbt_data");

    public TeleportationNBTData(final FriendlyByteBuf buffer) {
        this(buffer.readRegistryKey(), buffer.readItem(), buffer.readVec3(), buffer.readFloat(), buffer.readFloat(), Arrays.toString(buffer.readByteArray()), Arrays.toString(buffer.readByteArray()), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeResourceKey(levelKey);
        buffer.writeItem(stack);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
        buffer.writeByteArray(name.getBytes(StandardCharsets.UTF_8));
        buffer.writeByteArray(name.getBytes(StandardCharsets.UTF_8));
        buffer.writeBoolean(remove);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}