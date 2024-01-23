package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record C2SInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "interact_entity_packet");

    @SuppressWarnings("unused")
    public C2SInteractEntityPacket(final FriendlyByteBuf buffer) {
        this(buffer.readItem(), buffer.readUUID(), buffer.readBoolean());
    }

    @Override
    public void write(final @NotNull FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
        buffer.writeUUID(entityUUID);
        buffer.writeBoolean(hand);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}