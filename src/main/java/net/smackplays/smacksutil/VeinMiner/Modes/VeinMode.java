package net.smackplays.smacksutil.VeinMiner.Modes;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

public class VeinMode {
    public static ArrayList<BlockPos> toBreak = new ArrayList<>();
    public static ArrayList<BlockPos> oldToBreak = new ArrayList<>();
    public static ArrayList<BlockPos> checked = new ArrayList<>();
    public static int oldRadius;
    public static BlockPos oldSourcePos;
    public static Block oldToMatch;
    public String ModeName = "";

    VeinMode() {
    }

    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, boolean isExactMatch) {
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

        surrounding[8] = curr.up();
        surrounding[9] = curr.up().north();
        surrounding[10] = curr.up().north().east();
        surrounding[11] = curr.up().east();
        surrounding[12] = curr.up().east().south();
        surrounding[13] = curr.up().south();
        surrounding[14] = curr.up().south().west();
        surrounding[15] = curr.up().west();
        surrounding[16] = curr.up().north().west();

        surrounding[17] = curr.down();
        surrounding[18] = curr.down().north();
        surrounding[19] = curr.down().north().east();
        surrounding[20] = curr.down().east();
        surrounding[21] = curr.down().east().south();
        surrounding[22] = curr.down().south();
        surrounding[23] = curr.down().south().west();
        surrounding[24] = curr.down().west();
        surrounding[25] = curr.down().north().west();
        return surrounding;
    }

    public boolean checkMatch(boolean isExactMatch, BlockPos pos, World world, PlayerEntity player, Block toMatch, TagKey<Block> tag) {
        boolean contains = toBreak.contains(pos);
        boolean isInBlacklist = world.getBlockState(pos).isIn(ModTags.Blocks.VEIN_BLACKLIST);
        boolean canHarvest = (player.canHarvest(world.getBlockState(pos)) || player.isCreative());
        if (isExactMatch || tag == null) {
            boolean isToMatch = world.getBlockState(pos).getBlock().equals(toMatch);
            return !contains && !isInBlacklist && canHarvest && isToMatch;
        } else {
            boolean isInTag = world.getBlockState(pos).isIn(tag);
            return !contains && !isInBlacklist && canHarvest && isInTag;
        }
    }

    public String getName() {
        return ModeName;
    }
}