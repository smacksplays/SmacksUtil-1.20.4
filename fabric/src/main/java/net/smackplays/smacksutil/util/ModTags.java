package net.smackplays.smacksutil.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.SmacksUtil;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CROP_BLOCKS =
                createTag("crop_blocks");
        public static final TagKey<Block> ORE_BLOCKS =
                createTag("ore_blocks");
        public static final TagKey<Block> VEGETATION_BLOCKS =
                createTag("vegetation_blocks");
        public static final TagKey<Block> STONE_BLOCKS =
                createTag("stone_blocks");
        public static final TagKey<Block> DIRT_BLOCKS =
                createTag("dirt_blocks");
        public static final TagKey<Block> TREE_BLOCKS =
                createTag("tree_blocks");
        public static final TagKey<Block> VEIN_BLACKLIST =
                createTag("veinminer_blacklist");
        public static final TagKey<Block> VEIN_MINING =
                createTag("veinminer_mineing");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(Constants.MOD_ID, name));
        }
    }

}

