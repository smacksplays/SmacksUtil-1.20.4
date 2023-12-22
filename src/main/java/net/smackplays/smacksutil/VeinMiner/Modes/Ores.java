package net.smackplays.smacksutil.VeinMiner.Modes;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Ores extends VeinMode {
    public Ores() {
        ModeName = "Ores";
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
        if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.ORE_BLOCKS)) {
            tag = ModTags.Blocks.ORE_BLOCKS;
        }
        ores(sourcePos, world, player, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private void ores(BlockPos curr, World world, PlayerEntity player, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        if (checked.contains(curr)) {
            return;
        } else {
            checked.add(curr);
        }
        if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
            if (!toBreak.contains(curr)) {
                toBreak.add(curr);
            }
        }
        BlockPos[] surrounding = getSurrounding(curr);
        for (BlockPos pos : surrounding) {
            if (checkMatch(isExactMatch, pos, world, player, toMatch, tag)) {
                ores(pos, world, player, isExactMatch, toMatch, tag);
            }
        }
    }
}