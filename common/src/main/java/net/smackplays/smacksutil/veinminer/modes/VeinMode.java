package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.List;

public class VeinMode {
    public static ArrayList<BlockPos> toBreak = new ArrayList<>();
    public static ArrayList<BlockPos> toCheck = new ArrayList<>();
    public static ArrayList<BlockPos> oldToBreak = new ArrayList<>();
    public static ArrayList<BlockPos> checked = new ArrayList<>();
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

    public ArrayList<BlockPos> getSurrounding(BlockPos curr, Level world, boolean isExactMatch, Block block, TagKey<Block> tag) {
        ArrayList<BlockPos> surrounding = new ArrayList<>();
        BlockPos c = curr.north();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.north().east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.east().south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.south().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.north().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().north();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().north().east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().east().south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().south().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.above().north().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().north();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().north().east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().east();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().east().south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().south();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().south().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
        }
        c = curr.below().north().west();
        if ((isExactMatch && world.getBlockState(c).is(block)) || (!isExactMatch && world.getBlockState(c).is(tag))){
            surrounding.add(c);
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

