package net.smackplays.smacksutil.VeinMiner;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.VeinMiner.Modes.*;
import net.smackplays.smacksutil.events.KeyInputHandler;
import net.smackplays.smacksutil.util.CustomRenderLayer;
import net.smackplays.smacksutil.util.ModTags;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Miner {
    public static int MAX_RADIUS = 6;
    public static ArrayList<BlockPos> toBreak;
    public static VeinMode mode;
    public static int radius = 2;
    public static VeinMode ShapelessMode = new Shapeless();
    public static VeinMode ShapelessVerticalMode = new ShapelessVertical();
    public static VeinMode TunnelMode = new Tunnel();
    public static VeinMode MineshaftMode = new Mineshaft();
    public static VeinMode CropsMode = new Crops();
    public static VeinMode OresMode = new Ores();
    public static VeinMode VegetationMode = new Vegetation();
    public static VeinMode TreeMode = new Trees();
    public static VeinMode[] modeArray = new VeinMode[]{
            ShapelessMode,
            ShapelessVerticalMode,
            TunnelMode,
            MineshaftMode
    };
    public static int currMode = 0;
    public boolean renderPreview = false;
    public boolean isMining = false;
    public boolean isDrawing = false;
    public boolean isExactMatch = true;

    public Miner() {

    }

    public static void scroll(double vertical) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        Screen scr = MinecraftClient.getInstance().currentScreen;
        if (MinecraftClient.getInstance().player != null && scr == null && KeyInputHandler.veinKey.isPressed()) {
            if (player.isSneaking()) {
                currMode += (int) vertical;
                player.getInventory().selectedSlot = player.getInventory().selectedSlot + (int) vertical;
                if (currMode > modeArray.length - 1) currMode = modeArray.length - 1;
                else if (currMode < 0) currMode = 0;
                player.sendMessage(Text.literal("Mode: " + modeArray[currMode].getName()), true);
                SmacksUtil.veinMiner.setMode();
            } else {
                radius += (int) vertical;
                player.getInventory().selectedSlot = player.getInventory().selectedSlot + (int) vertical;
                if (radius > MAX_RADIUS) radius = MAX_RADIUS;
                else if (radius < 1) radius = 1;
                player.sendMessage(Text.literal("Radius: " + radius), true);
            }
        }
    }

    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ) {
        MatrixStack.Entry entry = matrices.peek();
        shape.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
            float k = (float) (maxX - minX);
            float l = (float) (maxY - minY);
            float m = (float) (maxZ - minZ);
            float n = MathHelper.sqrt(k * k + l * l + m * m);
            k /= n;
            l /= n;
            m /= n;
            vertexConsumer.vertex(entry.getPositionMatrix(), (float) (minX + offsetX), (float) (minY + offsetY), (float) (minZ + offsetZ)).color(1.0F, 1.0F, 1.0F, 0.8F).normal(entry.getNormalMatrix(), k, l, m).next();
            vertexConsumer.vertex(entry.getPositionMatrix(), (float) (maxX + offsetX), (float) (maxY + offsetY), (float) (maxZ + offsetZ)).color(1.0F, 1.0F, 1.0F, 0.8F).normal(entry.getNormalMatrix(), k, l, m).next();
        });
    }

    public ArrayList<BlockPos> getBlocks(World worldIn, PlayerEntity playerIn, BlockPos sourcePosIn) {
        BlockState sourceBlockState = worldIn.getBlockState(sourcePosIn);
        ArrayList<BlockPos> matching;
        setMode();

        if (sourceBlockState.isIn(ModTags.Blocks.CROP_BLOCKS)) {
            matching = (ArrayList<BlockPos>) CropsMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (sourceBlockState.isIn(ModTags.Blocks.ORE_BLOCKS)) {
            matching = (ArrayList<BlockPos>) OresMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (sourceBlockState.isIn(ModTags.Blocks.VEGETATION_BLOCKS)) {
            matching = (ArrayList<BlockPos>) VegetationMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch).clone();
        } else if (sourceBlockState.isIn(ModTags.Blocks.TREE_BLOCKS)) {
            matching = (ArrayList<BlockPos>) TreeMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch).clone();
        } else {
            matching = (ArrayList<BlockPos>) mode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        }

        return matching;
    }

    public void veinMiner(World world, PlayerEntity player, BlockPos sourcePos) {
        if (isMining) return;
        isMining = true;
        boolean drop = true;
        BlockState sourceBlockState = world.getBlockState(sourcePos);
        boolean replaceSeeds = sourceBlockState.isIn(ModTags.Blocks.CROP_BLOCKS);
        ItemStack mainHandStack = player.getMainHandStack();
        Item mainHand = player.getMainHandStack().getItem();
        boolean mainHandIsTool = ToolItem.class.isAssignableFrom(mainHand.getClass());
        if (player.isCreative()) drop = false;

        toBreak = getBlocks(world, player, sourcePos);

        int maxDMG = mainHandStack.getMaxDamage();

        for (BlockPos curr : toBreak) {
            BlockState currBlockState = world.getBlockState(curr);
            Block currBlock = world.getBlockState(curr).getBlock();
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;

            if (mainHandIsTool && mainHandStack.getDamage() >= maxDMG - 10) {
                isMining = false;
                player.sendMessage(Text.literal("Mining stopped! Tool would break ;)"), true);
                return;
            }

            LootContextParameterSet.Builder builder =
                    new LootContextParameterSet.Builder((ServerWorld) world)
                            .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(curr))
                            .add(LootContextParameters.TOOL, mainHandStack)
                            .addOptional(LootContextParameters.BLOCK_ENTITY, currBlockEntity);

            List<ItemStack> dropList = currBlockState.getDroppedStacks(builder);

            boolean canHarvest = (player.canHarvest(currBlockState) || player.isCreative());
            if (dropList != null && canHarvest) {
                if (drop) {
                    for (ItemStack stack : dropList) {
                        world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), stack));
                    }
                }
                //world.breakBlock(curr.north(), false, player);
                world.setBlockState(curr, Blocks.AIR.getDefaultState());
                if (mainHandStack.isDamageable()) {
                    mainHandStack.damage(1, player.getRandom(), (ServerPlayerEntity) player);
                }

                if (replaceSeeds) {
                    world.setBlockState(curr, currBlock.getDefaultState());
                }
            }
        }

        isMining = false;
    }

    public void setMode() {
        mode = modeArray[currMode];
        MAX_RADIUS = mode.MAX_RADIUS;
        if (radius > MAX_RADIUS) radius = MAX_RADIUS;
    }

    public void togglePreview() {
        renderPreview = !renderPreview;
    }

    public boolean getRenderPreview() {
        return renderPreview;
    }

    public boolean isToBreakEmpty() {
        if (toBreak == null) return true;
        return toBreak.isEmpty();
    }

    public void drawOutline(MatrixStack matrices, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            World world, PlayerEntity player) {
        if (isDrawing) return;
        isDrawing = true;
        toBreak = (ArrayList<BlockPos>) SmacksUtil.veinMiner.getBlocks(world, player, pos).clone();

        while (toBreak.size() >= 100){
            toBreak.remove(toBreak.size() - 1);
        }
        VoxelShape shape = combine(world, pos, (ArrayList<BlockPos>) toBreak.clone());

        VertexConsumer vertex = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().getBuffer(CustomRenderLayer.LINES);

        drawCuboidShapeOutline(matrices, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }

    public VoxelShape combine(World world, BlockPos pos, List<BlockPos> toRender) {
        VoxelShape shape = VoxelShapes.empty();
        for (BlockPos pos1 : toRender) {
            VoxelShape cubeShape = world.getBlockState(pos1).getOutlineShape(world, pos1);
            double offsetX = pos1.getX() - pos.getX();
            double offsetY = pos1.getY() - pos.getY();
            double offsetZ = pos1.getZ() - pos.getZ();
            shape = VoxelShapes.union(shape, cubeShape.offset(offsetX, offsetY, offsetZ));
        }
        return shape;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public boolean isMining() {
        return isMining;
    }

    public void toggleExactMatch() {
        isExactMatch = !isExactMatch;
    }

    public boolean isExactMatch() {
        return isExactMatch;
    }
}
