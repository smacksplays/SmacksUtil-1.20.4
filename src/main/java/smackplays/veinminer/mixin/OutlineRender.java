package smackplays.veinminer.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.Mouse;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.ArrayVoxelShape;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import smackplays.veinminer.Miner;
import smackplays.veinminer.VeinMiner;
import smackplays.veinminer.VeinMode;
import smackplays.veinminer.events.KeyInputHandler;

import java.util.*;

@Mixin(WorldRenderer.class)
public abstract class OutlineRender {

    @Inject(at = @At("HEAD"), method = "drawBlockOutline", cancellable = true)
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer,
                          Entity entity, double cameraX, double cameraY,
                          double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci){
        if(VeinMiner.veinMiner.getRenderPreview() && entity.isPlayer()){
            if (!KeyInputHandler.veinKey.isPressed()) return;
            //VeinMiner.outlineRender.setParams(matrices, vertexConsumer, (PlayerEntity) entity, entity.getWorld(), pos, cameraX, cameraY, cameraZ);
            //VeinMiner.veinMiner.setToRender(pos, entity.getWorld(), (PlayerEntity) entity);
            VeinMiner.veinMiner.drawOutline(matrices, vertexConsumer, entity, cameraX, cameraY,
                    cameraZ, pos, state, entity.getWorld(), (PlayerEntity) entity);
            ci.cancel();
        }
    }
}
