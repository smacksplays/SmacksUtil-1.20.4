package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.platform.Services;

public class BreakBlockPacket {
    private final BlockPos pos;

    public BreakBlockPacket(BlockPos p) {
        pos = p;
    }

    public BreakBlockPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (Services.KEY_HANDLER.isVeinKeyDown()){
            Services.VEIN_MINER.veinMiner(context.getSender().level(), context.getSender(), pos);
        }
    }
}
