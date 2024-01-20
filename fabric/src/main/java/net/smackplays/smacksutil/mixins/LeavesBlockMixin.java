package net.smackplays.smacksutil.mixins;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.smackplays.smacksutil.ModClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {
    @Final
    @Shadow
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
    @Final
    @Shadow
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource, CallbackInfo ci) {
        if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7) {
            FriendlyByteBuf packet = PacketByteBufs.create();
            packet.writeBlockPos(pos);
            if (ClientPlayNetworking.canSend(ModClient.BREAK_BLOCK_REQUEST_ID)){
                ClientPlayNetworking.send(ModClient.BREAK_BLOCK_REQUEST_ID, packet);
            }
        }
    }
}