package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SSetBlockAirPacket(BlockPos pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "set_block_air_packet");

    public C2SSetBlockAirPacket(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}