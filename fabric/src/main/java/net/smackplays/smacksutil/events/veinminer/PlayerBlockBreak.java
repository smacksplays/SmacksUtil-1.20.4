package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.Constants.MOD_ID;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!Services.PLATFORM.isClient()){
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            BlockState s = world.getBlockState(pos);
            ServerPlayNetworking.send((ServerPlayer) player, new ResourceLocation(MOD_ID, "test"), buf);
            return false;
        }
        if (!Services.KEY_HANDLER.veinKey.isDown() || Services.VEIN_MINER.isDrawing() || Services.VEIN_MINER.isMining()) {
            Services.VEIN_MINER.isMining = false;
            return true;
        }
        Services.VEIN_MINER.veinMiner(world, player, pos);
        return false;
    }
}
