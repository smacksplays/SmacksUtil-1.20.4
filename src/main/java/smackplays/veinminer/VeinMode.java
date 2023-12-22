package smackplays.veinminer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class VeinMode {
    public static String name;
    public static ArrayList<BlockPos> toBreak = new ArrayList<BlockPos>();
    public static ArrayList<BlockPos> oldtoBreak = new ArrayList<BlockPos>();
    public static ArrayList<BlockPos> checked = new ArrayList<BlockPos>();
    public static World world;
    public static ItemStack tool;
    public static PlayerEntity player;
    public static int radius;
    public static int oldradius;
    public static BlockPos sourcePos;
    public static BlockPos oldsourcePos;
    public static Block toMatch;
    public static Block oldtoMatch;

    public static final ArrayList<Block> oreList = new ArrayList<>(){{
        add(Blocks.DIAMOND_ORE);
        add(Blocks.DEEPSLATE_DIAMOND_ORE);

        add(Blocks.EMERALD_ORE);
        add(Blocks.DEEPSLATE_EMERALD_ORE);

        add(Blocks.IRON_ORE);
        add(Blocks.DEEPSLATE_IRON_ORE);

        add(Blocks.GOLD_ORE);
        add(Blocks.DEEPSLATE_GOLD_ORE);

        add(Blocks.COAL_ORE);
        add(Blocks.DEEPSLATE_COAL_ORE);

        add(Blocks.LAPIS_ORE);
        add(Blocks.DEEPSLATE_LAPIS_ORE);

        add(Blocks.COPPER_ORE);
        add(Blocks.DEEPSLATE_COPPER_ORE);

        add(Blocks.REDSTONE_ORE);
        add(Blocks.DEEPSLATE_REDSTONE_ORE);
    }};

    public static final ArrayList<Block> cropList = new ArrayList<>(){{
        add(Blocks.CARROTS);
        add(Blocks.WHEAT);
        add(Blocks.POTATOES);
        add(Blocks.BEETROOTS);
    }};

    public static final ArrayList<Block> vegetationList = new ArrayList<>(){{
        add(Blocks.SHORT_GRASS);
        add(Blocks.TALL_GRASS);
        add(Blocks.SEAGRASS);
        add(Blocks.TALL_SEAGRASS);
        add(Blocks.DANDELION);
        add(Blocks.POPPY);
        add(Blocks.BLUE_ORCHID);
        add(Blocks.ALLIUM);
        add(Blocks.AZURE_BLUET);
        add(Blocks.RED_TULIP);
        add(Blocks.ORANGE_TULIP);
        add(Blocks.WHITE_TULIP);
        add(Blocks.PINK_TULIP);
        add(Blocks.OXEYE_DAISY);
        add(Blocks.CORNFLOWER);
        add(Blocks.LILY_OF_THE_VALLEY);
        add(Blocks.SUNFLOWER);
        add(Blocks.LILAC);
        add(Blocks.ROSE_BUSH);
        add(Blocks.PEONY);
    }};

    public static final ArrayList<Block> blackList = new ArrayList<>(){{
        add(Blocks.BEDROCK);
        add(Blocks.AIR);
    }};

    VeinMode(String nameIn){
        name = nameIn;
    }

    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();

        if(oldtoBreak != null && oldsourcePos == sourcePos && oldradius == radius && oldtoMatch == toMatch) return oldtoBreak;

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    public String getName(){
        return "";
    }

    public void setPlayer(ClientPlayerEntity playerIn) {
        player = playerIn;
    }

    public void setWorld(ClientWorld worldIn) {
        world = worldIn;
    }
}

class Shapeless extends VeinMode{
    public final String ModeName = "Shapeless";
    Shapeless(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        oldtoBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;

        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        if (!oldtoBreak.isEmpty() && oldsourcePos.equals(sourcePos) && oldradius == radius && oldtoMatch.equals(toMatch)) return oldtoBreak;
        if (vegetationList.contains(toMatch)){
            vegetation(sourcePos);
        } else {
            shapeless(sourcePos);
        }

        return toBreak;
    }

    private static void shapeless(BlockPos curr){
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
                shapeless(pos);
            }
        }
    }

    private static void vegetation(BlockPos curr){
        BlockPos pos = new BlockPos(curr.getX() - radius, curr.getY() - 2, curr.getZ() - radius);

        for(int i = 0; i < radius * 2 + 1; i++){
            for(int j = 0; j < radius * 2 + 1; j++){
                for(int u = 0; u < 5; u++){
                    Block posBlock = world.getBlockState(pos).getBlock();
                    if(vegetationList.contains(world.getBlockState(pos).getBlock())){
                        toBreak.add(pos);
                    }
                    pos = pos.add(0,1,0);
                }
                pos = pos.add(0,-5,1);
            }
            pos = pos.add(1,0,-radius * 2 - 1);
        }
    }

    @Override
    public String getName(){
        return ModeName;
    }
}

class Ores extends VeinMode{
    public final String ModeName = "Ores";
    public static List<Block> toMatchList = new ArrayList<Block>();
    public static List<Block> oldtoMatchList = new ArrayList<Block>();
    Ores(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        toMatchList.clear();
        if (toMatch.equals(Blocks.IRON_ORE) || toMatch.equals(Blocks.DEEPSLATE_IRON_ORE)) {
            toMatchList.add(Blocks.IRON_ORE);
            toMatchList.add(Blocks.DEEPSLATE_IRON_ORE);
        }
        else if (toMatch.equals(Blocks.COAL_ORE) || toMatch.equals(Blocks.DEEPSLATE_COAL_ORE)) {
            toMatchList.add(Blocks.COAL_ORE);
            toMatchList.add(Blocks.DEEPSLATE_COAL_ORE);
        }
        else if (toMatch.equals(Blocks.REDSTONE_ORE) || toMatch.equals(Blocks.DEEPSLATE_REDSTONE_ORE)) {
            toMatchList.add(Blocks.REDSTONE_ORE);
            toMatchList.add(Blocks.DEEPSLATE_REDSTONE_ORE);
        }
        else if (toMatch.equals(Blocks.LAPIS_ORE) || toMatch.equals(Blocks.DEEPSLATE_LAPIS_ORE)) {
            toMatchList.add(Blocks.LAPIS_ORE);
            toMatchList.add(Blocks.DEEPSLATE_LAPIS_ORE);
        }
        else if (toMatch.equals(Blocks.DIAMOND_ORE) || toMatch.equals(Blocks.DEEPSLATE_DIAMOND_ORE)) {
            toMatchList.add(Blocks.DIAMOND_ORE);
            toMatchList.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        }
        else if (toMatch.equals(Blocks.EMERALD_ORE) || toMatch.equals(Blocks.DEEPSLATE_EMERALD_ORE)) {
            toMatchList.add(Blocks.EMERALD_ORE);
            toMatchList.add(Blocks.DEEPSLATE_EMERALD_ORE);
        }
        else if (toMatch.equals(Blocks.GOLD_ORE) || toMatch.equals(Blocks.DEEPSLATE_GOLD_ORE)) {
            toMatchList.add(Blocks.GOLD_ORE);
            toMatchList.add(Blocks.DEEPSLATE_GOLD_ORE);
        }
        else if (toMatch.equals(Blocks.COPPER_ORE) || toMatch.equals(Blocks.DEEPSLATE_COPPER_ORE)) {
            toMatchList.add(Blocks.COPPER_ORE);
            toMatchList.add(Blocks.DEEPSLATE_COPPER_ORE);
        }
        if(!oldtoBreak.isEmpty() && oldsourcePos == sourcePos && oldradius == radius && oldtoMatchList.contains(toMatch)) return oldtoBreak;
        if(oreList.contains(toMatch)){
            ores(sourcePos);
        }

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    private static void ores(BlockPos curr){
        if(checked.contains(curr)){
            return;
        }
        else{
            checked.add(curr);
        }
        if(toMatchList.contains(world.getBlockState(curr).getBlock())){
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
            if(toMatchList.contains(world.getBlockState(pos).getBlock())){
                ores(pos);
            }
        }
    }

    @Override
    public String getName(){
        return ModeName;
    }
}

class ShapelessVertical extends VeinMode{
    public final String ModeName = "ShapelessVertical";
    ShapelessVertical(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        if(!oldtoBreak.isEmpty() && oldsourcePos == sourcePos && oldradius == radius && oldtoMatch == toMatch) return oldtoBreak;
        shapeless_hor(sourcePos);

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    private static void shapeless_hor(BlockPos curr){
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
                shapeless_hor(pos);
            }
        }
    }

    @Override
    public String getName(){
        return ModeName;
    }
}

class Tunnel extends VeinMode{
    public final String ModeName = "Tunnel";
    Tunnel(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        if(!oldtoBreak.isEmpty() && oldsourcePos == sourcePos && oldradius == radius && oldtoMatch == toMatch) return oldtoBreak;
        tunnel(sourcePos, player.getHorizontalFacing());

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    private static void tunnel(BlockPos curr, Direction direction){
        for(int i = 0; i < radius * 2; i++){
            if(!toBreak.contains(curr) && !blackList.contains(world.getBlockState(curr).getBlock())){
                toBreak.add(curr);
            }
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
    @Override
    public String getName(){
        return ModeName;
    }
}

class Mineshaft extends VeinMode{
    public final String ModeName = "Mineshaft";
    Mineshaft(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        if(!oldtoBreak.isEmpty() && oldsourcePos == sourcePos && oldradius == radius && oldtoMatch == toMatch) return oldtoBreak;
        mineshaft(sourcePos, player.getHorizontalFacing());

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    private static void mineshaft(BlockPos curr, Direction direction){
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
    }

    @Override
    public String getName(){
        return ModeName;
    }
}

class Crops extends VeinMode{
    public final String ModeName = "Crops";
    Crops(String nameIn) {
        super(nameIn);
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn, int radiusIn, ItemStack toolIn) {
        if(worldIn == null || playerIn == null ||sourcePosIn == null)   return toBreak;
        toBreak.clear();
        checked.clear();
        world = worldIn;
        tool = toolIn;
        player = playerIn;
        radius = radiusIn;
        sourcePos = sourcePosIn;
        toMatch = world.getBlockState(sourcePosIn).getBlock();
        if(!oldtoBreak.isEmpty() && oldsourcePos == sourcePos && oldradius == radius && oldtoMatch == toMatch) return oldtoBreak;
        crops(sourcePos);

        oldtoBreak = toBreak;
        oldradius = radius;
        oldsourcePos = sourcePos;
        oldtoMatch = toMatch;
        return toBreak;
    }

    private static void crops(BlockPos curr){
        BlockPos pos = new BlockPos(curr.getX() - radius, curr.getY() - 2, curr.getZ() - radius);

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


        /*
        if(curr.getX() > sourcePos.getX() + radius
                || curr.getX() < sourcePos.getX() - radius){
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
                BlockState state = world.getBlockState(curr);
                if (CropBlock.class.isAssignableFrom(state.getBlock().getClass())) {
                    CropBlock crop = (CropBlock) state.getBlock();
                    if (crop.isMature(state)){
                        toBreak.add(curr);
                    }
                }
            }
        }
        BlockPos[] surrounding = new BlockPos[17];
        surrounding[0] = curr.north();
        surrounding[1] = curr.north().east();
        surrounding[2] = curr.east();
        surrounding[3] = curr.east().south();
        surrounding[4] = curr.south();
        surrounding[5] = curr.south().west();
        surrounding[6] = curr.west();
        surrounding[7] = curr.north().west();

        surrounding[8] = curr.down();
        surrounding[9] = curr.down().north();
        surrounding[10] = curr.down().north().east();
        surrounding[11] = curr.down().east();
        surrounding[12] = curr.down().east().south();
        surrounding[13] = curr.down().south();
        surrounding[14] = curr.down().south().west();
        surrounding[15] = curr.down().west();
        surrounding[16] = curr.down().north().west();
        for(BlockPos pos : surrounding){
            if(world.getBlockState(pos).getBlock().equals(toMatch)){
                crops(pos);
            }
        }*/
    }

    @Override
    public String getName(){
        return ModeName;
    }
}