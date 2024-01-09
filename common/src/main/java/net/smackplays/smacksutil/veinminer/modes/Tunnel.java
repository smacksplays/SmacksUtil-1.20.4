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
public class Tunnel extends VeinMode {
    public Tunnel() {
        ModeName = "Tunnel";
        MAX_RADIUS = 12;
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

        tunnel(sourcePos, player.getDirection(), radius, player, world, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(sourcePos));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;
        oldIsExactMatch = isExactMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    public void tunnel(BlockPos curr, Direction direction, int radius, Player player,
                       Level world, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        for (int i = 0; i < radius * 2; i++) {
            if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
                toBreak.add(curr);
                if (checkMatch(isExactMatch, curr.below(), world, player, toMatch, tag)) {
                    toBreak.add(curr.below());
                }
                if (direction.equals(Direction.NORTH)) {
                    curr = curr.north(1);
                }
                if (direction.equals(Direction.SOUTH)) {
                    curr = curr.south(1);
                }
                if (direction.equals(Direction.EAST)) {
                    curr = curr.east(1);
                }
                if (direction.equals(Direction.WEST)) {
                    curr = curr.west(1);
                }
            }
        }
    }
}