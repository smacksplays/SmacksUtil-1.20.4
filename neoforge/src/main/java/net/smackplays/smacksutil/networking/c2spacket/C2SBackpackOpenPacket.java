package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SBackpackOpenPacket(int slot) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, Constants.C_BACKPACK_OPEN_REQUEST);

    @SuppressWarnings("unused")
    public C2SBackpackOpenPacket(final FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    @Override
    public void write(final @NotNull FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}