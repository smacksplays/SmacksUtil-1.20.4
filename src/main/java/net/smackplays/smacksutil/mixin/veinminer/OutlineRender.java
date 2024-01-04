package net.smackplays.smacksutil.mixin.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.events.KeyInputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class OutlineRender {

    @Inject(at = @At("HEAD"), method = "renderHitOutline", cancellable = true)
    private void drawBlockOutline(PoseStack matrices, VertexConsumer vertexConsumer,
                                  Entity entity, double cameraX, double cameraY,
                                  double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (SmacksUtil.veinMiner.getRenderPreview() && entity.isAlwaysTicking()) {
            if (!KeyInputHandler.veinKey.isDown()) return;
            SmacksUtil.veinMiner.setMode();
            SmacksUtil.veinMiner.drawOutline(matrices, cameraX, cameraY,
                    cameraZ, pos, entity.level(), (Player) entity);
            if (!SmacksUtil.veinMiner.isToBreakEmpty()) {
                ci.cancel();
            }
        }
    }
}
