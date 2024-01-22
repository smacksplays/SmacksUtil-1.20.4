package net.smackplays.smacksutil.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public interface IServerPacketSender {
    void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos);
}
