package net.smackplays.smacksutil.VeinMiner;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.List;

public class VeinMode {
    public String ModeName = "";
    public static ArrayList<BlockPos> toBreak = new ArrayList<BlockPos>();
    public static ArrayList<BlockPos> oldToBreak = new ArrayList<BlockPos>();
    public static ArrayList<BlockPos> checked = new ArrayList<BlockPos>();

    public static int oldRadius;
    public static BlockPos oldSourcePos;
    public static Block oldToMatch;


    public static final ArrayList<Block> blackList = new ArrayList<>(){{
        add(Blocks.BEDROCK);
        add(Blocks.AIR);
    }};

    VeinMode(){
    }

    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        return new ArrayList<BlockPos>();
    }

    public String getName(){
        return ModeName;
    }

}

@SuppressWarnings("unchecked")
class Shapeless extends VeinMode{
    Shapeless() {
        ModeName = "Shapeless";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        };

        shapeless(sourcePos, sourcePos, radius, toMatch, world);

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private static void shapeless(BlockPos curr, BlockPos sourcePos, int radius, Block toMatch, World world){
        if(curr.getX() > sourcePos.getX() + radius
                || curr.getX() < sourcePos.getX() - radius){
            return;
        }
        if(curr.getY() > sourcePos.getY() + radius
                || curr.getY() < sourcePos.getY() - radius){
            return;
        }
        if(curr.getZ() > sourcePos.getZ() + radius
                || curr.getZ() < sourcePos.getZ() - radius){
            return;
        }
        if(checked.contains(curr)){
            return;
        }
        else{
            checked.add(curr);
        }
        if(world.getBlockState(curr).getBlock() == toMatch){
            if(!toBreak.contains(curr) && !blackList.contains(world.getBlockState(curr).getBlock())){
                toBreak.add(curr);
            }
        }
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
        for(BlockPos pos : surrounding){
            if(world.getBlockState(pos).getBlock().equals(toMatch)){
                shapeless(pos, sourcePos, radius, toMatch, world);
            }
        }
    }

}

@SuppressWarnings("unchecked")
class Vegetation extends VeinMode{
    Vegetation() {
        ModeName = "Vegetation";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        BlockPos pos = new BlockPos(sourcePos.getX() - radius, sourcePos.getY() - 2, sourcePos.getZ() - radius);

        for(int i = 0; i < radius * 2 + 1; i++){
            for(int j = 0; j < radius * 2 + 1; j++){
                for(int u = 0; u < 5; u++){
                    if (world.getBlockState(pos).isIn(ModTags.Blocks.VEGETATION_BLOCKS)){
                        toBreak.add(pos);
                    }
                    pos = pos.add(0,1,0);
                }
                pos = pos.add(0,-5,1);
            }
            pos = pos.add(1,0,-radius * 2 - 1);
        }

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }
}

@SuppressWarnings("unchecked")
class Ores extends VeinMode{
    Ores() {
        ModeName = "Ores";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        ores(sourcePos, world);

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private static void ores(BlockPos curr, World world){
        if(checked.contains(curr)){
            return;
        }
        else{
            checked.add(curr);
        }
        if (world.getBlockState(curr).isIn(ModTags.Blocks.ORE_BLOCKS)){
            if(!toBreak.contains(curr)){
                toBreak.add(curr);
            }
        }
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
        for(BlockPos pos : surrounding){
            if (world.getBlockState(pos).isIn(ModTags.Blocks.ORE_BLOCKS)){
                ores(pos, world);
            }
        }
    }
}

@SuppressWarnings("unchecked")
class ShapelessVertical extends VeinMode{
    ShapelessVertical() {
        ModeName = "ShapelessVertical";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        shapeless_hor(sourcePos, sourcePos, radius, player, world, toMatch);

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private static void shapeless_hor(BlockPos curr, BlockPos sourcePos, int radius,
                                      PlayerEntity player, World world, Block toMatch){
        if(curr.getX() > sourcePos.getX() + radius
                || curr.getX() < sourcePos.getX() - radius){
            return;
        }
        double p = player.getY();
        if(curr.getY() > sourcePos.getY() + radius
                || curr.getY() < player.getY()){
            return;
        }
        if(curr.getZ() > sourcePos.getZ() + radius
                || curr.getZ() < sourcePos.getZ() - radius){
            return;
        }
        if(checked.contains(curr)){
            return;
        }
        else{
            checked.add(curr);
        }
        if(world.getBlockState(curr).getBlock() == toMatch){
            if(!toBreak.contains(curr) && !blackList.contains(world.getBlockState(curr).getBlock())){
                toBreak.add(curr);
            }
        }
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
        for(BlockPos pos : surrounding){
            if(world.getBlockState(pos).getBlock().equals(toMatch)){
                shapeless_hor(pos, sourcePos, radius, player, world, toMatch);
            }
        }
    }

}

@SuppressWarnings("unchecked")
class Tunnel extends VeinMode{
    Tunnel() {
        ModeName = "Tunnel";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        tunnel(sourcePos, player.getHorizontalFacing(), radius, world);

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private static void tunnel(BlockPos curr, Direction direction, int radius, World world){
        for(int i = 0; i < radius * 2; i++){
            if(!toBreak.contains(curr) && !blackList.contains(world.getBlockState(curr).getBlock())){
                toBreak.add(curr);
                if(!toBreak.contains(curr.down()) && !blackList.contains(world.getBlockState(curr).getBlock())){
                    toBreak.add(curr.down());
                }
                if(direction.equals(Direction.NORTH)){
                    curr = curr.north(1);
                }
                if(direction.equals(Direction.SOUTH)){
                    curr = curr.south(1);
                }
                if(direction.equals(Direction.EAST)){
                    curr = curr.east(1);
                }
                if(direction.equals(Direction.WEST)){
                    curr = curr.west(1);
                }
            }
        }
    }
}

@SuppressWarnings("unchecked")
class Mineshaft extends VeinMode{
    Mineshaft() {
        ModeName = "Mineshaft";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        BlockPos curr = sourcePos;
        Direction direction = player.getHorizontalFacing();

        for(int i = 0; i < radius *2; i++){
            if(!toBreak.contains(curr) && !blackList.contains(world.getBlockState(curr).getBlock())){
                toBreak.add(curr);
            }
            if(direction.equals(Direction.NORTH)){
                toBreak.add(curr.north(1));
                toBreak.add(curr.north(2));
                toBreak.add(curr.north(3));
                curr = curr.north(1);
            }
            if(direction.equals(Direction.SOUTH)){
                toBreak.add(curr.south(1));
                toBreak.add(curr.south(2));
                toBreak.add(curr.south(3));
                curr = curr.south(1);
            }
            if(direction.equals(Direction.EAST)){
                toBreak.add(curr.east(1));
                toBreak.add(curr.east(2));
                toBreak.add(curr.east(3));
                curr = curr.east(1);
            }
            if(direction.equals(Direction.WEST)){
                toBreak.add(curr.west(1));
                toBreak.add(curr.west(2));
                toBreak.add(curr.west(3));
                curr = curr.west(1);
            }
            curr = curr.down();
        }

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private static void mineshaft(BlockPos curr, Direction direction, int radius, World world){

    }
}

@SuppressWarnings("unchecked")
class Crops extends VeinMode{
    Crops() {
        ModeName = "Crops";
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, ItemStack tool) {
        if(world == null || player == null ||sourcePos == null)   return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        BlockPos pos = new BlockPos(sourcePos.getX() - radius, sourcePos.getY() - 2, sourcePos.getZ() - radius);

        for(int x = 0; x < radius * 2 + 1; x++){
            for(int z = 0; z < radius * 2 + 1; z++){
                for(int y = 0; y < 5; y++){
                    BlockState state = world.getBlockState(pos);
                    if (CropBlock.class.isAssignableFrom(state.getBlock().getClass())) {
                        CropBlock crop = (CropBlock) state.getBlock();
                        if (crop.isMature(state)){
                            toBreak.add(pos);
                        }
                    }
                    pos = pos.add(0,1,0);
                }
                pos = pos.add(0,-5,1);
            }
            pos = pos.add(1,0,-radius * 2 - 1);
        }

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }
}