package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;

import static net.smackplays.smacksutil.SmacksUtil.VEINMINER_SERVER_BLOCK_BREAK_REQUEST_ID;

public class FabricServerPacketSender implements IServerPacketSender {
    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        if (ServerPlayNetworking.canSend(player, VEINMINER_SERVER_BLOCK_BREAK_REQUEST_ID)){
            ServerPlayNetworking.send(player, VEINMINER_SERVER_BLOCK_BREAK_REQUEST_ID, buf);
        }
    }
}
