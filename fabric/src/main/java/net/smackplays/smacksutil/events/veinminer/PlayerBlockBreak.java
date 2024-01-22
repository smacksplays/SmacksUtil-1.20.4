package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        Services.PACKET_SENDER.sendToPlayerBlockBreakPacket((ServerPlayer) player, pos);
        return true;
    }
}
