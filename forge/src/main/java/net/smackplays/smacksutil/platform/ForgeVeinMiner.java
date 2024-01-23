package net.smackplays.smacksutil.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.platform.services.IVeinMiner;
import net.smackplays.smacksutil.util.CustomRenderLayer;

import java.util.ArrayList;

public class ForgeVeinMiner extends IVeinMiner {

    public ForgeVeinMiner() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            Level world, Player player) {
        if (isDrawing) return;
        isDrawing = true;
        setMode();
        ArrayList<BlockPos> toRender = (ArrayList<BlockPos>) toBreak.clone();
        //toBreak = (ArrayList<BlockPos>) Services.VEIN_MINER.getBlocks(world, player, pos).clone();
        int maxRenderBlocks = Services.CONFIG.getMaxRenderBlocks();
        if (toRender.size() > maxRenderBlocks) {
            toRender = new ArrayList<>(toRender.subList(0, maxRenderBlocks));
        }

        VoxelShape shape = combine(world, pos, (ArrayList<BlockPos>) toRender.clone());

        VertexConsumer vertex = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(CustomRenderLayer.LINES);

        drawCuboidShapeOutline(pose, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }
}
