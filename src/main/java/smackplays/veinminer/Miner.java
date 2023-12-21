package smackplays.veinminer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import smackplays.veinminer.events.KeyInputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Miner {
    public static BlockPos sourceBlock;
    public static ArrayList<BlockPos> toBreak;
    public static VeinMode mode;
    public static final int MAXRADIUS = 6;
    public static int radius = 2;
    public static boolean isInit = false;
    public boolean renderPreview = false;
    public boolean isMining = false;
    public boolean isDrawing = false;
    public static VeinMode[] modeArray = new VeinMode[]{
            new Shapeless("Shapeless"),
            new ShapelessVertical("Shapeless Vertical"),
            new Ores("Ores"),
            new Tunnel("Tunnel"),
            new Mineshaft("Mineshaft"),
            new Crops("Crops")
    };
    public static int currMode = 0;

    public Miner(){

    }

    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn){
        Block block = worldIn.getBlockState(sourcePosIn).getBlock();
        if(VeinMode.cropList.contains(block)){
            mode = modeArray[5];
        } else if (VeinMode.oreList.contains(block)){
            mode = modeArray[2];
        }
        ArrayList<BlockPos> matching = mode.getBlocks(worldIn, playerIn, sourcePosIn, radius, playerIn.getMainHandStack());
        mode = modeArray[currMode];
        return matching;
    }

    public void veinMiner(World world, PlayerEntity player, BlockPos sourceBlockPos){
        if (isMining) return;
        isMining = true;
        boolean drop = true;
        boolean replaceSeeds = false;
        String unbreaking = "";
        ItemStack mainHandStack = player.getMainHandStack();
        Item mainHand = player.getMainHandStack().getItem();
        NbtList enchants = mainHandStack.getEnchantments();

        BlockState sourceBlockState = world.getBlockState(sourceBlockPos);
        Block sourceBlock = sourceBlockState.getBlock();

        if(player.isCreative()) drop = false;

        mode = modeArray[currMode];
        if(VeinMode.cropList.contains(sourceBlock)){
            mode = modeArray[5];
            replaceSeeds = true;
        } else if (VeinMode.oreList.contains(sourceBlock)){
            mode = modeArray[2];
        }

        toBreak = (ArrayList<BlockPos>)mode.getBlocks(world, player, sourceBlockPos, radius, mainHandStack).clone();

        for(Object nbt : enchants){
            NbtCompound n = (NbtCompound)nbt;
            if(n.get("id").asString().equals("minecraft:unbreaking")){
                unbreaking = n.get("lvl").asString();
            }
        }
        int damage = switch (unbreaking) {
            case "1s" -> toBreak.size() / 2;
            case "2s" -> toBreak.size() / 3;
            case "3s" -> toBreak.size() / 4;
            default -> toBreak.size();
        };
        int currDMG = mainHandStack.getDamage();
        int maxDMG = mainHandStack.getMaxDamage();
        if(maxDMG != 0){
            if(KeyInputHandler.veinKey.isPressed() && !toBreak.isEmpty()){
                int i = 0;
                for( BlockPos curr : toBreak){
                    BlockState currBlockState = world.getBlockState(curr);
                    Block currBlock = currBlockState.getBlock();
                    BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;

                    if(currDMG + i == maxDMG - 2) {
                        isMining = false;
                        player.getMainHandStack().setDamage(currDMG + i);
                        toBreak.clear();
                        if(isMining){
                            int a = 0;
                        }
                        return;
                    }
                    if(mainHand.canMine(currBlockState, world, curr, player)){
                        if (drop){
                            Block.dropStacks(currBlockState, world, player.getBlockPos().up(), currBlockEntity, player, mainHandStack);
                        }
                        world.breakBlock(curr, false, player);
                    }
                    i++;
                }
                toBreak.clear();
                player.getMainHandStack().setDamage(currDMG + damage);
            }
        } else {
            if(KeyInputHandler.veinKey.isPressed() && !toBreak.isEmpty()){
                for( BlockPos curr : toBreak){
                    BlockState currBlockState = world.getBlockState(curr);
                    Block currBlock = currBlockState.getBlock();
                    BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;
                    if(mainHand.canMine(currBlockState, world, curr, player)){
                        if (drop){
                            Block.dropStacks(currBlockState, world, player.getBlockPos().up(), currBlockEntity, player, mainHandStack);
                        }
                        world.breakBlock(curr, false, player);
                        if(replaceSeeds){
                            world.setBlockState(curr, currBlock.getDefaultState());
                        }
                    }
                }
                toBreak.clear();
            }
        }
        isMining = false;
        mode = modeArray[currMode];
        if(isMining){
            int i = 0;
        }
    }
    public void setMode(){
        mode = modeArray[currMode];
    }

    public static void scroll(long window, double horizontal, double vertical){
        PlayerEntity player = MinecraftClient.getInstance().player;
        Screen scr = MinecraftClient.getInstance().currentScreen;
        if(MinecraftClient.getInstance().player != null && scr == null && KeyInputHandler.veinKey.isPressed()){
            if(player.isSneaking()){
                currMode += (int)vertical;
                player.getInventory().selectedSlot = player.getInventory().selectedSlot + (int)vertical;
                if(currMode > modeArray.length - 1) currMode = modeArray.length - 1;
                else if (currMode < 0) currMode = 0;
                player.sendMessage(Text.literal("Mode: " + modeArray[currMode].getName()), true);
                VeinMiner.veinMiner.setMode();
            } else {
                radius += (int)vertical;
                player.getInventory().selectedSlot = player.getInventory().selectedSlot + (int)vertical;
                if(radius > MAXRADIUS) radius = MAXRADIUS;
                else if (radius < 1) radius = 1;
                player.sendMessage(Text.literal("Radius: " + radius), true);
            }
        }
    }

    public void togglePreview() {
        renderPreview = !renderPreview;
    }

    public boolean getRenderPreview() {
        return renderPreview;
    }

    public void initModes(ClientWorld world, ClientPlayerEntity player) {
        isInit = true;
        for (VeinMode veinMode : modeArray) {
            veinMode.setPlayer(player);
            veinMode.setWorld(world);
        }
        setMode();
    }

    public boolean getInitState() {
        return isInit;
    }


    public void drawOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity,
                            double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            BlockState state, World world, PlayerEntity player, BlockPos pos1) {
        if(isDrawing) return;
        isDrawing = true;
        ArrayList<BlockPos> toRender = (ArrayList<BlockPos>) VeinMiner.veinMiner.getBlocks(world, player, pos).clone();
        VoxelShape shape = combine(world, pos,toRender);

        drawCuboidShapeOutline(matrices, vertexConsumer, shape,
                (double)pos.getX() - cameraX, (double)pos.getY() - cameraY, (double)pos.getZ() - cameraZ,
                0.0F, 0.0F, 0.0F, 0.4F);
        isDrawing = false;
    }

    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
        MatrixStack.Entry entry = matrices.peek();
        shape.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
            float k = (float)(maxX - minX);
            float l = (float)(maxY - minY);
            float m = (float)(maxZ - minZ);
            float n = MathHelper.sqrt(k * k + l * l + m * m);
            k /= n;
            l /= n;
            m /= n;
            vertexConsumer.vertex(entry.getPositionMatrix(), (float)(minX + offsetX), (float)(minY + offsetY), (float)(minZ + offsetZ)).color(red, green, blue, alpha).normal(entry.getNormalMatrix(), k, l, m).next();
            vertexConsumer.vertex(entry.getPositionMatrix(), (float)(maxX + offsetX), (float)(maxY + offsetY), (float)(maxZ + offsetZ)).color(red, green, blue, alpha).normal(entry.getNormalMatrix(), k, l, m).next();
        });
    }

    public VoxelShape combine(World world, BlockPos pos, List<BlockPos> toRender) {
        VoxelShape shape = VoxelShapes.empty();
        for (BlockPos pos1 : toRender){
            VoxelShape cubeShape = world.getBlockState(pos1).getOutlineShape(world,pos1);
            double offsetX = pos1.getX() - pos.getX();
            double offsetY = pos1.getY() - pos.getY();
            double offsetZ = pos1.getZ() - pos.getZ();
            shape = VoxelShapes.union(shape, cubeShape.offset(offsetX, offsetY, offsetZ));
        }
        return shape;
    }

    public boolean isDrawing(){
        return isDrawing;
    }
    public boolean isMining(){
        return isMining;
    }
}
