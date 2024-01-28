package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SVeinMinerBreakPacket(ItemStack stack, BlockPos pos, boolean isCreative, boolean replaceSeeds) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "veinminer_break_packet");

    @SuppressWarnings("unused")
    public C2SVeinMinerBreakPacket(final FriendlyByteBuf buffer) {
        this(buffer.readItem(),  buffer.readBlockPos(), buffer.readBoolean(), buffer.readBoolean());
        }

    @Override
    public void write(final @NotNull FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(replaceSeeds);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
