package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Mineshaft extends VeinMode {
    public Mineshaft() {
        ModeName = "Mineshaft";
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

        TagKey<Block> tag = null;
        if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.VEIN_MINING)) {
            tag = ModTags.Blocks.VEIN_MINING;
        } else if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.STONE_BLOCKS)) {
            tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.DIRT_BLOCKS)) {
            tag = ModTags.Blocks.DIRT_BLOCKS;
        }

        mineshaft(sourcePos, player.getHorizontalFacing(), radius, player, world, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    public void mineshaft(BlockPos curr, Direction direction, int radius, PlayerEntity player, World world, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        for (int i = 0; i < radius * 2; i++) {
            if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
                toBreak.add(curr);
                for (int j = 1; j < 4; j++) {
                    if (checkMatch(isExactMatch, curr.offset(direction, j), world, player, toMatch, tag)) {
                        toBreak.add(curr.offset(direction, j));
                    }
                }
                curr = curr.offset(direction, 1);
            }
            curr = curr.down();
        }
    }
}