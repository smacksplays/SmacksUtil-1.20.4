package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.networking.s2cpacket.S2CPlayerBlockBreakPacket;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;

public class ForgeServerPacketSender implements IServerPacketSender {

    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        PacketHandler.sendToPlayer(new S2CPlayerBlockBreakPacket(pos), player);
    }
}
