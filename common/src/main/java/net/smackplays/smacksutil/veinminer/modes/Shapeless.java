package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.BlockPosComparator;
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
        toCheck.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch) && oldIsExactMatch == isExactMatch) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        TagKey<Block> tag = null;
        if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            tag = ModTags.Blocks.DIRT_BLOCKS;
        }
        shapeless(new BlockPos(sourcePos), world, player, radius, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(sourcePos));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;
        oldIsExactMatch = isExactMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private void shapeless(BlockPos sourcePos, Level world, Player player, int radius, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        toCheck.add(sourcePos);
        while (!toCheck.isEmpty()) {
            BlockPos currPos = toCheck.get(0);
            if (checkMatch(isExactMatch, currPos, world, player, toMatch, tag)) {
                toBreak.add(currPos);
                ArrayList<BlockPos> surrounding = getSurrounding(currPos, world, isExactMatch, toMatch, tag);
                toCheck.addAll(surrounding);
            }
            toCheck.remove(currPos);
            // remove duplicates
            ArrayList<BlockPos> newList = new ArrayList<>();
            for (BlockPos p : (ArrayList<BlockPos>) toCheck.clone()) {
                if (!newList.contains(p) && !toBreak.contains(p)
                        && !((p.getX() > sourcePos.getX() + radius) || (p.getX() < sourcePos.getX() - radius))
                        && !((p.getY() > sourcePos.getY() + radius) || (p.getY() < sourcePos.getY() - radius))
                        && !((p.getZ() > sourcePos.getZ() + radius) || (p.getZ() < sourcePos.getZ() - radius))) {
                    newList.add(p);
                }
            }
            toCheck = newList;
            toCheck.sort(new BlockPosComparator(sourcePos));
        }
    }

    @Override
    public boolean doRender(int radius) {
        if (Services.CONFIG != null) {
            return radius <= Services.CONFIG.getMaxRenderShapelessRadius();
        }
        return false;
    }
}