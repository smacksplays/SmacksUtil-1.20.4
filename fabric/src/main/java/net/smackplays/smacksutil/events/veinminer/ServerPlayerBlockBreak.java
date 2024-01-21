package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.platform.services.IKeyHandler;
import org.jetbrains.annotations.Nullable;

public class ServerPlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (Services.KEY_HANDLER.veinKey.isDown()){
            if (ServerPlayNetworking.canSend((ServerPlayer) player, SmacksUtil.VEINMINER_SERVER_BREAK_REQUEST_ID)){
                FriendlyByteBuf packet = PacketByteBufs.create();
                packet.writeBlockPos(pos);
                ServerPlayNetworking.send((ServerPlayer) player, SmacksUtil.VEINMINER_SERVER_BREAK_REQUEST_ID, packet);
            }
            return false;
        }
        return true;
    }
}
