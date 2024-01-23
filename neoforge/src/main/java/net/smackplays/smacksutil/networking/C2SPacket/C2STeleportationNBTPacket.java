package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2STeleportationNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "teleportation_nbt_packet");

    public C2STeleportationNBTPacket(final FriendlyByteBuf buffer) {
        this(buffer.readItem(), buffer.readVec3(), buffer.readFloat(), buffer.readFloat(), buffer.readUtf(), buffer.readUtf(), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
        buffer.writeUtf(name);
        buffer.writeUtf(dim);
        buffer.writeBoolean(remove);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}