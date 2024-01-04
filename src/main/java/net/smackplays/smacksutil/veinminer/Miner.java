package net.smackplays.smacksutil.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.ModConfig;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.events.KeyInputHandler;
import net.smackplays.smacksutil.util.CustomRenderLayer;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.veinminer.modes.*;

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
    public static ArrayList<VeinMode> modeList = new ArrayList<>() {{
        add(ShapelessMode);
        add(ShapelessVerticalMode);
        add(TunnelMode);
        add(MineshaftMode);
    }};
    public static int currMode = 0;
    public boolean renderPreview = false;
    public boolean isMining = false;
    public boolean isDrawing = false;
    public boolean isExactMatch = true;

    public Miner() {

    }

    public static void scroll(double vertical) {
        Player player = Minecraft.getInstance().player;
        Screen scr = Minecraft.getInstance().screen;

        ShapelessMode.MAX_RADIUS = ModConfig.INSTANCE.maxShapelessRadius;
        if (ModConfig.INSTANCE.enableShapelessVerticalMode && !modeList.contains(ShapelessVerticalMode)) {
            modeList.add(ShapelessVerticalMode);
        } else if (!ModConfig.INSTANCE.enableShapelessVerticalMode) {
            modeList.remove(ShapelessVerticalMode);
        }
        ShapelessVerticalMode.MAX_RADIUS = ModConfig.INSTANCE.maxShapelessVerticalRadius;
        if (ModConfig.INSTANCE.enableTunnelMode && !modeList.contains(TunnelMode)) {
            modeList.add(TunnelMode);
        } else if (!ModConfig.INSTANCE.enableTunnelMode) {
            modeList.remove(TunnelMode);
        }
        if (ModConfig.INSTANCE.enableMineshaftMode && !modeList.contains(MineshaftMode)) {
            modeList.add(MineshaftMode);
        } else if (!ModConfig.INSTANCE.enableMineshaftMode) {
            modeList.remove(MineshaftMode);
        }

        if (player != null && scr == null && KeyInputHandler.veinKey.isDown()) {
            if (player.isCrouching()) {
                currMode += (int) vertical;
                player.getInventory().selected = player.getInventory().selected + (int) vertical;
                if (currMode > modeList.size() - 1) currMode = modeList.size() - 1;
                else if (currMode < 0) currMode = 0;
                player.displayClientMessage(Component.literal("Mode: " + modeList.get(currMode).getName()), true);
                SmacksUtil.veinMiner.setMode();
            } else {
                radius += (int) vertical;
                player.getInventory().selected = player.getInventory().selected + (int) vertical;
                if (radius > MAX_RADIUS) radius = MAX_RADIUS;
                else if (radius < 1) radius = 1;
                player.displayClientMessage(Component.literal("Radius: " + radius), true);
            }
        }
    }

    private static void drawCuboidShapeOutline(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ) {
        PoseStack.Pose pose = poseStack.last();
        shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            float k = (float) (maxX - minX);
            float l = (float) (maxY - minY);
            float m = (float) (maxZ - minZ);
            float n = Mth.sqrt(k * k + l * l + m * m);
            k /= n;
            l /= n;
            m /= n;
            vertexConsumer.vertex(pose.pose(),
                            (float) (minX + offsetX),
                            (float) (minY + offsetY),
                            (float) (minZ + offsetZ))
                    .color(1.0F, 1.0F, 1.0F, 0.8F)
                    .normal(pose.normal(), k, l, m).endVertex();
            vertexConsumer.vertex(pose.pose(),
                            (float) (maxX + offsetX),
                            (float) (maxY + offsetY),
                            (float) (maxZ + offsetZ))
                    .color(1.0F, 1.0F, 1.0F, 0.8F)
                    .normal(pose.normal(), k, l, m).endVertex();
        });
    }

    public ArrayList<BlockPos> getBlocks(Level worldIn, Player playerIn, BlockPos sourcePosIn) {
        BlockState sourceBlockState = worldIn.getBlockState(sourcePosIn);
        ArrayList<BlockPos> matching;
        setMode();

        if (sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS)) {
            matching = (ArrayList<BlockPos>) CropsMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (sourceBlockState.is(ModTags.Blocks.ORE_BLOCKS)) {
            matching = (ArrayList<BlockPos>) OresMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (sourceBlockState.is(ModTags.Blocks.VEGETATION_BLOCKS)) {
            matching = (ArrayList<BlockPos>) VegetationMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch).clone();
        } else if (sourceBlockState.is(ModTags.Blocks.TREE_BLOCKS)) {
            matching = (ArrayList<BlockPos>) TreeMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch).clone();
        } else if (mode.equals(ShapelessMode)) {
            matching = (ArrayList<BlockPos>) ShapelessMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (mode.equals(ShapelessVerticalMode) && ModConfig.INSTANCE.enableShapelessVerticalMode) {
            matching = (ArrayList<BlockPos>) ShapelessVerticalMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (mode.equals(TunnelMode) && ModConfig.INSTANCE.enableTunnelMode) {
            matching = (ArrayList<BlockPos>) TunnelMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (mode.equals(MineshaftMode) && ModConfig.INSTANCE.enableMineshaftMode) {
            matching = (ArrayList<BlockPos>) MineshaftMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else {
            matching = new ArrayList<>();
        }

        return matching;
    }

    public void veinMiner(Level world, Player player, BlockPos sourcePos) {
        if (isMining) return;
        isMining = true;
        boolean drop = true;
        BlockState sourceBlockState = world.getBlockState(sourcePos);
        boolean replaceSeeds = sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS);
        ItemStack mainHandStack = player.getMainHandItem();
        Item mainHand = player.getMainHandItem().getItem();
        boolean mainHandIsTool = TieredItem.class.isAssignableFrom(mainHand.getClass());
        if (player.isCreative()) drop = false;

        toBreak = getBlocks(world, player, sourcePos);

        int maxDMG = mainHandStack.getMaxDamage();

        for (BlockPos curr : toBreak) {
            BlockState currBlockState = world.getBlockState(curr);
            Block currBlock = world.getBlockState(curr).getBlock();
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;

            if (mainHandIsTool && mainHandStack.getMaxDamage() >= maxDMG - 10) {
                isMining = false;
                player.displayClientMessage(Component.literal("Mining stopped! Tool would break ;)"), true);
                return;
            }

            LootParams.Builder builder =
                    new LootParams.Builder((ServerLevel) world)
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(curr))
                            .withParameter(LootContextParams.TOOL, mainHandStack)
                            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, currBlockEntity);

            List<ItemStack> dropList = currBlockState.getDrops(builder);

            boolean canHarvest = (player.hasCorrectToolForDrops(currBlockState) || player.isCreative());
            if (dropList != null && canHarvest) {
                if (drop) {
                    for (ItemStack stack : dropList) {
                        world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), stack));
                    }
                }
                //world.destroyBlock(curr.north(), drop, player);
                world.setBlockAndUpdate(curr, Blocks.AIR.defaultBlockState());
                if (mainHandStack.isDamageableItem()) {
                    mainHandStack.hurt(1, player.getRandom(), (ServerPlayer) player);
                }

                if (replaceSeeds) {
                    world.setBlockAndUpdate(curr, currBlock.defaultBlockState());
                }
            }
        }

        isMining = false;
    }

    public void setMode() {
        mode = modeList.get(currMode);
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

    public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            Level world, Player player) {
        if (isDrawing) return;
        isDrawing = true;
        toBreak = (ArrayList<BlockPos>) SmacksUtil.veinMiner.getBlocks(world, player, pos).clone();

        if (toBreak.size() > ModConfig.INSTANCE.maxRenderBlocks) {
            player.displayClientMessage(Component.literal("Rendering reduced by " + (toBreak.size() - ModConfig.INSTANCE.maxRenderBlocks)), true);
            toBreak = new ArrayList<>(toBreak.subList(0, ModConfig.INSTANCE.maxRenderBlocks));
        }

        VoxelShape shape = combine(world, pos, (ArrayList<BlockPos>) toBreak.clone());

        VertexConsumer vertex = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(CustomRenderLayer.LINES);

        drawCuboidShapeOutline(pose, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }

    public VoxelShape combine(Level world, BlockPos pos, List<BlockPos> toRender) {
        VoxelShape shape = Shapes.empty();
        for (BlockPos pos1 : toRender) {
            VoxelShape cubeShape = world.getBlockState(pos1).getShape(world, pos1);
            double offsetX = pos1.getX() - pos.getX();
            double offsetY = pos1.getY() - pos.getY();
            double offsetZ = pos1.getZ() - pos.getZ();
            shape = Shapes.or(shape, cubeShape.move(offsetX, offsetY, offsetZ));
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

    public VeinMode getMode() {
        return mode;
    }

    public int getRadius() {
        return radius;
    }
}
