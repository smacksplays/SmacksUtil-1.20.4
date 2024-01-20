package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

public class VeinMode {
    public static final ArrayList<BlockPos> toBreak = new ArrayList<>();
    public static ArrayList<BlockPos> toCheck = new ArrayList<>();
    public static ArrayList<BlockPos> oldToBreak = new ArrayList<>();
    public static final ArrayList<BlockPos> checked = new ArrayList<>();
    public static int oldRadius;
    public static BlockPos oldSourcePos;
    public static Block oldToMatch;
    public static boolean oldIsExactMatch;
    public String ModeName = "";
    public int MAX_RADIUS = 6;

    VeinMode() {
    }

    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        return new ArrayList<>();
    }

    public boolean checkForSurrounding(BlockPos curr, Level world, boolean isExactMatch, Block block, TagKey<Block> tag) {
        return ((isExactMatch || tag == null) && world.getBlockState(curr).is(block)) || (!(tag == null) && !isExactMatch && world.getBlockState(curr).is(tag));
    }

    public ArrayList<BlockPos> getSurrounding(BlockPos curr, Level world, boolean isExactMatch, Block block, TagKey<Block> tag) {
        ArrayList<BlockPos> surrounding = new ArrayList<>();
        if (checkForSurrounding(curr.north(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.north());
        }
        if (checkForSurrounding(curr.north().east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.north().east());
        }
        if (checkForSurrounding(curr.east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.east());
        }
        if (checkForSurrounding(curr.east().south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.east().south());
        }
        if (checkForSurrounding(curr.south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.south());
        }
        if (checkForSurrounding(curr.south().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.south().west());
        }
        if (checkForSurrounding(curr.west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.west());
        }
        if (checkForSurrounding(curr.north().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.north().west());
        }
        if (checkForSurrounding(curr.above(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above());
        }
        if (checkForSurrounding(curr.above().north(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().north());
        }
        if (checkForSurrounding(curr.above().north().east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().north().east());
        }
        if (checkForSurrounding(curr.above().east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().east());
        }
        if (checkForSurrounding(curr.above().east().south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().east().south());
        }
        if (checkForSurrounding(curr.above().south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().south());
        }
        if (checkForSurrounding(curr.above().south().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().south().west());
        }
        if (checkForSurrounding(curr.above().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().west());
        }
        if (checkForSurrounding(curr.above().north().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.above().north().west());
        }
        if (checkForSurrounding(curr.below(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below());
        }
        if (checkForSurrounding(curr.below().north(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().north());
        }
        if (checkForSurrounding(curr.below().north().east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().north().east());
        }
        if (checkForSurrounding(curr.below().east(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().east());
        }
        if (checkForSurrounding(curr.below().east().south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().east().south());
        }
        if (checkForSurrounding(curr.below().south(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().south());
        }
        if (checkForSurrounding(curr.below().south().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().south().west());
        }
        if (checkForSurrounding(curr.below().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().west());
        }
        if (checkForSurrounding(curr.below().north().west(), world, isExactMatch, block, tag)) {
            surrounding.add(curr.below().north().west());
        }
        return surrounding;
    }

    public boolean checkMatch(boolean isExactMatch, BlockPos pos, Level world, Player player, Block toMatch, TagKey<Block> tag) {
        boolean contains = toBreak.contains(pos);
        boolean isInBlacklist = world.getBlockState(pos).is(ModTags.Blocks.VEIN_BLACKLIST);
        boolean canHarvest = (player.hasCorrectToolForDrops(world.getBlockState(pos)) || player.isCreative());
        if (isExactMatch || tag == null) {
            boolean isToMatch = world.getBlockState(pos).getBlock().equals(toMatch);
            return !contains && !isInBlacklist && canHarvest && isToMatch;
        } else {
            boolean isInTag = world.getBlockState(pos).is(tag);
            return !contains && !isInBlacklist && canHarvest && isInTag;
        }
    }

    public String getName() {
        return ModeName;
    }

    public boolean doRender(int radius) {
        return true;
    }
}

