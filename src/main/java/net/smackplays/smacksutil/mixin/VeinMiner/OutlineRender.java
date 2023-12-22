package net.smackplays.smacksutil.mixin.VeinMiner;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.events.KeyInputHandler;

@Mixin(WorldRenderer.class)
public abstract class OutlineRender {

    @Inject(at = @At("HEAD"), method = "drawBlockOutline", cancellable = true)
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer,
                          Entity entity, double cameraX, double cameraY,
                          double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci){
        if(SmacksUtil.veinMiner.getRenderPreview() && entity.isPlayer()){
            if (!KeyInputHandler.veinKey.isPressed()) return;
            //SmacksUtil.outlineRender.setParams(matrices, vertexConsumer, (PlayerEntity) entity, entity.getWorld(), pos, cameraX, cameraY, cameraZ);
            //SmacksUtil.veinMiner.setToRender(pos, entity.getWorld(), (PlayerEntity) entity);
            SmacksUtil.veinMiner.setMode();
            SmacksUtil.veinMiner.drawOutline(matrices, vertexConsumer, entity, cameraX, cameraY,
                    cameraZ, pos, state, entity.getWorld(), (PlayerEntity) entity);
            ci.cancel();
        }
    }
}
