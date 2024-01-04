package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Vegetation extends VeinMode {
    public Vegetation() {
        ModeName = "Vegetation";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
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
                    if (world.getBlockState(pos).is(ModTags.Blocks.VEGETATION_BLOCKS) && player.hasCorrectToolForDrops(world.getBlockState(pos))) {
                        toBreak.add(pos);
                    }
                    pos = pos.offset(0, 1, 0);
                }
                pos = pos.offset(0, -5, 1);
            }
            pos = pos.offset(1, 0, -radius * 2 - 1);
        }

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }
}