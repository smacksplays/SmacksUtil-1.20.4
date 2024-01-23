package net.smackplays.smacksutil.networking.S2CPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record S2CBlockBreakPacket(BlockPos pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "break_block_packet");

    public S2CBlockBreakPacket(final FriendlyByteBuf buffer) {
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