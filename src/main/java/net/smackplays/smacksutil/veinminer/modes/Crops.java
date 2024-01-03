package net.smackplays.smacksutil.veinminer.modes;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Crops extends VeinMode {
    public Crops() {
        ModeName = "Crops";
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

        for (int x = 0; x < radius * 2 + 1; x++) {
            for (int z = 0; z < radius * 2 + 1; z++) {
                for (int y = 0; y < 5; y++) {
                    BlockState state = world.getBlockState(pos);
                    if (CropBlock.class.isAssignableFrom(state.getBlock().getClass())) {
                        CropBlock crop = (CropBlock) state.getBlock();
                        if (crop.isMaxAge(state) && player.hasCorrectToolForDrops(world.getBlockState(pos))) {
                            toBreak.add(pos);
                        }
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