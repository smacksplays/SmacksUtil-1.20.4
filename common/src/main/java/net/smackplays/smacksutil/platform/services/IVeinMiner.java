package net.smackplays.smacksutil.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.veinminer.modes.*;

import java.util.ArrayList;
import java.util.List;

public abstract class IVeinMiner {
    public static int MAX_RADIUS = 6;
    public static ArrayList<BlockPos> toBreak;
    public static VeinMode mode;
    public static int radius = 2;
    public static final VeinMode ShapelessMode = new Shapeless();
    public static final VeinMode ShapelessVerticalMode = new ShapelessVertical();
    public static final VeinMode TunnelMode = new Tunnel();
    public static final VeinMode MineshaftMode = new Mineshaft();
    public static final VeinMode CropsMode = new Crops();
    public static final VeinMode OresMode = new Ores();
    public static final VeinMode VegetationMode = new Vegetation();
    public static final VeinMode TreeMode = new Trees();
    public static final  ArrayList<VeinMode> modeList = new ArrayList<>() {{
        add(ShapelessMode);
        add(ShapelessVerticalMode);
        add(TunnelMode);
        add(MineshaftMode);
    }};
    public static int currMode = 0;
    public boolean renderPreview = false;
    public boolean isMining = false;
    public boolean isDrawing = false;
    public boolean isExactMatch = false;

    public static void drawCuboidShapeOutline(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ) {
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

    abstract public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                                     Level world, Player player);

    @SuppressWarnings("unchecked")
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
        } else if (mode.equals(ShapelessVerticalMode) && Services.CONFIG.isEnabledShapelessVerticalMode()) {
            matching = (ArrayList<BlockPos>) ShapelessVerticalMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (mode.equals(TunnelMode) && Services.CONFIG.isEnabledTunnelMode()) {
            matching = (ArrayList<BlockPos>) TunnelMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else if (mode.equals(MineshaftMode) && Services.CONFIG.isEnabledMineshaftMode()) {
            matching = (ArrayList<BlockPos>) MineshaftMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch).clone();
        } else {
            matching = new ArrayList<>();
        }

        return matching;
    }

    @SuppressWarnings("unchecked")
    public void veinMiner(Level world, Player player, BlockPos sourcePos) {
        if (isMining) return;
        isMining = true;
        boolean isCreative = false;
        BlockState sourceBlockState = world.getBlockState(sourcePos);
        boolean replaceSeeds = sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS);
        ItemStack mainHandStack = player.getMainHandItem();
        Item mainHand = player.getMainHandItem().getItem();
        boolean mainHandIsTool = TieredItem.class.isAssignableFrom(mainHand.getClass());
        if (player.isCreative()) isCreative = true;

        toBreak = (ArrayList<BlockPos>) getBlocks(world, player, sourcePos).clone();

        int maxDMG = mainHandStack.getMaxDamage();

        for (BlockPos curr : toBreak) {
            BlockState currBlockState = world.getBlockState(curr);
            Block currBlock = world.getBlockState(curr).getBlock();

            if (mainHandIsTool && mainHandStack.getDamageValue() == maxDMG - 1) {
                isMining = false;
                player.displayClientMessage(Component.literal("Mining stopped! Tool would break ;)"), true);
                return;
            }

            boolean canHarvest = (player.hasCorrectToolForDrops(currBlockState) || player.isCreative());
            if (canHarvest) {
                //world.destroyBlock(curr, !isCreative);
                Services.PACKET_SENDER.sendToServerVeinMinerBreakPacket(mainHandStack, curr, isCreative, replaceSeeds);
                /*
                world.setBlockAndUpdate(curr, Blocks.AIR.defaultBlockState());
                if (!isCreative) {
                    BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;
                    Block.dropResources(currBlockState, world, curr, currBlockEntity, null, ItemStack.EMPTY);
                    if (mainHandStack.isDamageableItem()) {
                        mainHandStack.hurt(1, player.getRandom(), (ServerPlayer) player);
                    }
                }
                if (replaceSeeds) {
                    world.setBlockAndUpdate(curr, currBlock.defaultBlockState());
                }
                */
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

    public VeinMode getMode(Level world, BlockPos pos) {
        BlockState sourceBlockState = world.getBlockState(pos);
        if (sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS)) {
            return CropsMode;
        } else if (sourceBlockState.is(ModTags.Blocks.ORE_BLOCKS)) {
            return OresMode;
        } else if (sourceBlockState.is(ModTags.Blocks.VEGETATION_BLOCKS)) {
            return VegetationMode;
        } else if (sourceBlockState.is(ModTags.Blocks.TREE_BLOCKS)) {
            return TreeMode;
        } else if (mode.equals(ShapelessMode)) {
            return ShapelessMode;
        } else if (mode.equals(ShapelessVerticalMode) && Services.CONFIG.isEnabledShapelessVerticalMode()) {
            return ShapelessVerticalMode;
        } else if (mode.equals(TunnelMode) && Services.CONFIG.isEnabledTunnelMode()) {
            return TunnelMode;
        } else if (mode.equals(MineshaftMode) && Services.CONFIG.isEnabledMineshaftMode()) {
            return MineshaftMode;
        }
        return mode;
    }

    public void scroll(double vertical) {
        Player player = Minecraft.getInstance().player;
        Screen scr = Minecraft.getInstance().screen;

        ShapelessMode.MAX_RADIUS = Services.CONFIG.getMaxShapelessRadius();
        if (Services.CONFIG.isEnabledShapelessVerticalMode() && !modeList.contains(ShapelessVerticalMode)) {
            modeList.add(ShapelessVerticalMode);
        } else if (!Services.CONFIG.isEnabledShapelessVerticalMode()) {
            modeList.remove(ShapelessVerticalMode);
        }
        ShapelessVerticalMode.MAX_RADIUS = Services.CONFIG.getMaxShapelessVerticalRadius();
        if (Services.CONFIG.isEnabledTunnelMode() && !modeList.contains(TunnelMode)) {
            modeList.add(TunnelMode);
        } else if (!Services.CONFIG.isEnabledTunnelMode()) {
            modeList.remove(TunnelMode);
        }
        if (Services.CONFIG.isEnabledMineshaftMode() && !modeList.contains(MineshaftMode)) {
            modeList.add(MineshaftMode);
        } else if (!Services.CONFIG.isEnabledMineshaftMode()) {
            modeList.remove(MineshaftMode);
        }

        if (player != null && scr == null && IKeyHandler.veinKey.isDown()) {
            if (player.isCrouching()) {
                currMode += (int) vertical;
                player.getInventory().selected = player.getInventory().selected + (int) vertical;
                if (currMode > modeList.size() - 1) currMode = modeList.size() - 1;
                else if (currMode < 0) currMode = 0;
                player.displayClientMessage(Component.literal("Mode: " + modeList.get(currMode).getName()), true);
                Services.VEIN_MINER.setMode();
            } else {
                radius += (int) vertical;
                player.getInventory().selected = player.getInventory().selected + (int) vertical;
                if (radius > MAX_RADIUS) radius = MAX_RADIUS;
                else if (radius < 1) radius = 1;
                player.displayClientMessage(Component.literal("Radius: " + radius), true);
            }
        }
    }

    public int getRadius() {
        return radius;
    }
}
