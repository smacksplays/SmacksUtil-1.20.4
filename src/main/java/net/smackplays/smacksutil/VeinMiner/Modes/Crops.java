package net.smackplays.smacksutil.VeinMiner.Modes;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Crops extends VeinMode {
    public Crops() {
        ModeName = "Crops";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        if (world == null || player == null || sourcePos == null) return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        BlockPos pos = new BlockPos(sourcePos.getX() - radius, sourcePos.getY() - 2, sourcePos.getZ() - radius);

        for (int x = 0; x < radius * 2 + 1; x++) {
            for (int z = 0; z < radius * 2 + 1; z++) {
                for (int y = 0; y < 5; y++) {
                    BlockState state = world.getBlockState(pos);
                    if (CropBlock.class.isAssignableFrom(state.getBlock().getClass())) {
                        CropBlock crop = (CropBlock) state.getBlock();
                        if (crop.isMature(state) && player.canHarvest(world.getBlockState(pos))) {
                            toBreak.add(pos);
                        }
                    }
                    pos = pos.add(0, 1, 0);
                }
                pos = pos.add(0, -5, 1);
            }
            pos = pos.add(1, 0, -radius * 2 - 1);
        }

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }
}