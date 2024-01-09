package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.BlockPosComparator;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Mineshaft extends VeinMode {
    public Mineshaft() {
        ModeName = "Mineshaft";
        MAX_RADIUS = 10;
    }

    @Override
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        if (world == null || player == null || sourcePos == null) return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        toCheck.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch) && oldIsExactMatch == isExactMatch) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        TagKey<Block> tag = null;
        if (world.getBlockState(sourcePos).is(ModTags.Blocks.VEIN_MINING)) {
            tag = ModTags.Blocks.VEIN_MINING;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            tag = ModTags.Blocks.DIRT_BLOCKS;
        }

        mineshaft(sourcePos, player.getDirection(), radius, player, world, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(sourcePos));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;
        oldIsExactMatch = isExactMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    public void mineshaft(BlockPos curr, Direction direction, int radius, Player player, Level world, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        for (int i = 0; i < radius * 2; i++) {
            if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
                toBreak.add(curr);
                for (int j = 1; j < 4; j++) {
                    if (checkMatch(isExactMatch, curr.relative(direction, j), world, player, toMatch, tag)) {
                        toBreak.add(curr.relative(direction, j));
                    }
                }
                curr = curr.relative(direction, 1);
            }
            curr = curr.below();
        }
    }
}