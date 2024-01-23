package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.networking.S2CPacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;

import java.util.Objects;

public class NeoForgeServerPacketSender implements IServerPacketSender {

    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        player.connection.send(new S2CBlockBreakPacket(pos));
    }
}
