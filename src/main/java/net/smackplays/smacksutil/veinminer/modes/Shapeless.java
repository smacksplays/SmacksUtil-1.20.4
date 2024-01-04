package net.smackplays.smacksutil.veinminer.modes;

import net.smackplays.smacksutil.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Shapeless extends VeinMode {
    public Shapeless() {
        ModeName = "Shapeless";
        MAX_RADIUS = 4;
    }

    @Override
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        if (world == null || player == null || sourcePos == null) return (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        TagKey<Block> tag = null;
        if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            tag = ModTags.Blocks.DIRT_BLOCKS;
        }

        shapeless(sourcePos, sourcePos, radius, world, player, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private void shapeless(BlockPos curr, BlockPos sourcePos, int radius, Level world,
                           Player player, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        if (curr.getX() > sourcePos.getX() + radius
                || curr.getX() < sourcePos.getX() - radius) {
            return;
        }
        if (curr.getY() > sourcePos.getY() + radius
                || curr.getY() < sourcePos.getY() - radius) {
            return;
        }
        if (curr.getZ() > sourcePos.getZ() + radius
                || curr.getZ() < sourcePos.getZ() - radius) {
            return;
        }
        if (checked.contains(curr)) {
            return;
        } else {
            checked.add(curr);
        }
        if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
            toBreak.add(curr);
        }
        BlockPos[] surrounding = getSurrounding(curr);
        for (BlockPos pos : surrounding) {
            if (checkMatch(isExactMatch, pos, world, player, toMatch, tag)) {
                shapeless(pos, sourcePos, radius, world, player, isExactMatch, toMatch, tag);
            }
        }
    }

    @Override
    public boolean doRender(int radius) {
        return radius > ModConfig.INSTANCE.maxRenderShapelessRadius;
    }
}