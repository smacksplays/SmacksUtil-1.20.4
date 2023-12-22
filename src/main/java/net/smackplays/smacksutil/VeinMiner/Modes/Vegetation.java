package net.smackplays.smacksutil.VeinMiner.Modes;


import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Vegetation extends VeinMode {
    public Vegetation() {
        ModeName = "Vegetation";
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

        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                for (int u = 0; u < 5; u++) {
                    if (world.getBlockState(pos).isIn(ModTags.Blocks.VEGETATION_BLOCKS) && player.canHarvest(world.getBlockState(pos))) {
                        toBreak.add(pos);
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