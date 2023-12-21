package smackplays.veinminer.events;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import smackplays.veinminer.VeinMiner;
import smackplays.veinminer.VeinMode;

import java.util.List;

public class OutlineRender implements WorldRenderEvents.AfterTranslucent {

    public static PlayerEntity player;
    public static BlockPos pos;
    public static MatrixStack matrices;
    public static VertexConsumer vertexConsumer;
    public static double cameraX;
    public static double cameraY;
    public static double cameraZ;

    public void setPos(BlockPos posIn){
        pos = posIn;
    }
    @Override
    public void afterTranslucent(WorldRenderContext context) {
        if(VeinMiner.veinMiner.getRenderPreview()){
            if (pos == null || VeinMode.world == null || VeinMode.player == null || !KeyInputHandler.veinKey.isPressed()) return;

            //List<BlockPos> toRender = VeinMiner.veinMiner.getBlocks(context.world(), player, pos);
            //VoxelShape shape = combine(context.world(), pos, toRender);

            //drawCuboidShapeOutline(matrices, vertexConsumer, shape, cameraX, cameraY, cameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
        }



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

    public void setParams(MatrixStack matricesIn, VertexConsumer vertexConsumerIn, PlayerEntity playerIn,
                          double cameraXIn, double cameraYIn, double cameraZIn) {
        player = playerIn;
        matrices = matricesIn;
        vertexConsumer = vertexConsumerIn;
        cameraX = cameraXIn;
        cameraY = cameraYIn;
        cameraZ = cameraZIn;
    }
}
