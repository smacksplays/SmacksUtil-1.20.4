package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SVeinMinerBreakPacket {
    //private final ItemStack stack;
    private final BlockPos pos;
    private final boolean isCreative;
    private final boolean replaceSeeds;

    public C2SVeinMinerBreakPacket(ItemStack s, BlockPos p, boolean isC, boolean isR) {
        //stack = s;
        pos = p;
        isCreative = isC;
        replaceSeeds = isR;
    }

    public C2SVeinMinerBreakPacket(FriendlyByteBuf buffer) {
        //stack = buffer.readItem();
        pos = buffer.readBlockPos();
        isCreative = buffer.readBoolean();
        replaceSeeds = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        //buffer.writeItem(stack);
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(replaceSeeds);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            Level world = player.level();
            BlockState currBlockState = world.getBlockState(pos);

            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            if (!isCreative) {
                BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(pos) : null;
                Block.dropResources(currBlockState, world, pos, currBlockEntity, null, ItemStack.EMPTY);
                /*if (stack.isDamageableItem()) {
                    stack.hurt(1, player.getRandom(), player);
                }*/
            }
            if (replaceSeeds) {
                world.setBlockAndUpdate(pos, currBlockState.getBlock().defaultBlockState());
            }
        });
    }
}
