package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SetBlockAirPacket {
    private final BlockPos pos;

    public SetBlockAirPacket(BlockPos p) {
        pos = p;
    }

    public SetBlockAirPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        Level world = player.level();
        if (world.isClientSide) return;
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
