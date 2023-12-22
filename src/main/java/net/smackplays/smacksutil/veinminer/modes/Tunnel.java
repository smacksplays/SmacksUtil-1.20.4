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
public class Tunnel extends VeinMode {
    public Tunnel() {
        ModeName = "Tunnel";
        MAX_RADIUS = 12;
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

        tunnel(sourcePos, player.getHorizontalFacing(), radius, player, world, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    public void tunnel(BlockPos curr, Direction direction, int radius, PlayerEntity player,
                       World world, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        for (int i = 0; i < radius * 2; i++) {
            if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
                toBreak.add(curr);
                if (checkMatch(isExactMatch, curr.down(), world, player, toMatch, tag)) {
                    toBreak.add(curr.down());
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