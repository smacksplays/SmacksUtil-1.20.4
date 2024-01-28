package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class C2SVeinMinerBreakPacketHandler {private static final C2SVeinMinerBreakPacketHandler INSTANCE = new C2SVeinMinerBreakPacketHandler();

    public static C2SVeinMinerBreakPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2SVeinMinerBreakPacket data, final PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            if (context.level().isPresent() && context.player().isPresent()) {
                ServerLevel level = (ServerLevel) context.level().get();
                Player player = context.player().get();
                Level world = player.level();
                BlockState currBlockState = world.getBlockState(data.pos());

                world.setBlockAndUpdate(data.pos(), Blocks.AIR.defaultBlockState());
                if (!data.isCreative()) {
                    BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(data.pos()) : null;
                    Block.dropResources(currBlockState, world, data.pos(), currBlockEntity, null, ItemStack.EMPTY);
                    if (data.stack().isDamageableItem()) {
                        data.stack().hurt(1, player.getRandom(), (ServerPlayer) player);
                    }
                }
                if (data.replaceSeeds()) {
                    world.setBlockAndUpdate(data.pos(), currBlockState.getBlock().defaultBlockState());
                }
            }
        });
    }
}
