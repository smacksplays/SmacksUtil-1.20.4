package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.platform.Services;

public class C2SBreakBlockPacket {
    private final BlockPos pos;

    @SuppressWarnings("unused")
    public C2SBreakBlockPacket(BlockPos p) {
        pos = p;
    }

    public C2SBreakBlockPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null){
            if (Services.KEY_HANDLER.isVeinKeyDown()){
                Services.VEIN_MINER.veinMiner(context.getSender().level(), context.getSender(), pos);
            }
        }
    }
}
