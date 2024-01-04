package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

public class VeinMode {
    public static ArrayList<BlockPos> toBreak = new ArrayList<>();
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

    public BlockPos[] getSurrounding(BlockPos curr) {
        BlockPos[] surrounding = new BlockPos[26];
        surrounding[0] = curr.north();
        surrounding[1] = curr.north().east();
        surrounding[2] = curr.east();
        surrounding[3] = curr.east().south();
        surrounding[4] = curr.south();
        surrounding[5] = curr.south().west();
        surrounding[6] = curr.west();
        surrounding[7] = curr.north().west();

        surrounding[8] = curr.above();
        surrounding[9] = curr.above().north();
        surrounding[10] = curr.above().north().east();
        surrounding[11] = curr.above().east();
        surrounding[12] = curr.above().east().south();
        surrounding[13] = curr.above().south();
        surrounding[14] = curr.above().south().west();
        surrounding[15] = curr.above().west();
        surrounding[16] = curr.above().north().west();

        surrounding[17] = curr.below();
        surrounding[18] = curr.below().north();
        surrounding[19] = curr.below().north().east();
        surrounding[20] = curr.below().east();
        surrounding[21] = curr.below().east().south();
        surrounding[22] = curr.below().south();
        surrounding[23] = curr.below().south().west();
        surrounding[24] = curr.below().west();
        surrounding[25] = curr.below().north().west();
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

