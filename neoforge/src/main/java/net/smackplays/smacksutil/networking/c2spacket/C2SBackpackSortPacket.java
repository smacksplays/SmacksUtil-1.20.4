package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SBackpackSortPacket(ItemStack stack) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, Constants.C_BACKPACK_SORT_REQUEST);

    @SuppressWarnings("unused")
    public C2SBackpackSortPacket(final FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    @Override
    public void write(final @NotNull FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}