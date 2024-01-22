package net.smackplays.smacksutil.networking.S2CPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class PlayerBlockBreakPacket {
    private final BlockPos pos;

    public PlayerBlockBreakPacket(BlockPos p) {
        pos = p;
    }

    public PlayerBlockBreakPacket(FriendlyByteBuf buffer) {
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
        world.destroyBlock(pos, true);
    }
}
