package net.smackplays.smacksutil.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.event.KeyInputHandler;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.CustomRenderLayer;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Miner extends AMiner {

    public Miner() {

    }

    @Override
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

    @Override
    public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            Level world, Player player) {
        if (isDrawing) return;
        isDrawing = true;
        toBreak = (ArrayList<BlockPos>) SmacksUtil.veinMiner.getBlocks(world, player, pos).clone();
        int maxRenderBlocks = Services.CONFIG.getMaxRenderBlocks();
        if (toBreak.size() > maxRenderBlocks) {
            player.displayClientMessage(Component.literal("Rendering reduced by " + (toBreak.size() - maxRenderBlocks)), true);
            toBreak = new ArrayList<>(toBreak.subList(0, maxRenderBlocks));
        }

        VoxelShape shape = combine(world, pos, (ArrayList<BlockPos>) toBreak.clone());

        VertexConsumer vertex = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(CustomRenderLayer.LINES);

        drawCuboidShapeOutline(pose, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }
}
