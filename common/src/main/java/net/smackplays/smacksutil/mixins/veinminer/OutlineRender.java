package net.smackplays.smacksutil.mixins.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.platform.Services;
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
        if (Services.KEY_HANDLER.isVeinKeyDown()){
            if (Services.VEIN_MINER.isAcceptUpdate(pos)){
                Services.VEIN_MINER.updateBlocks(entity.level(), (Player) entity, pos);
            }
            if (Services.VEIN_MINER.isRenderPreview()){
                Services.VEIN_MINER.drawOutline(matrices, cameraX, cameraY,
                        cameraZ, pos, entity.level(), (Player) entity);
            }
        }
        /*
        if (Services.VEIN_MINER.isRenderPreview() && entity.isAlwaysTicking()) {
            if (!IKeyHandler.veinKey.isDown()) return;
            Services.VEIN_MINER.setMode();
            if (!Services.VEIN_MINER.getMode(entity.level(), pos).doRender(Services.VEIN_MINER.getRadius()))
                return;
            Services.VEIN_MINER.drawOutline(matrices, cameraX, cameraY,
                    cameraZ, pos, entity.level(), (Player) entity);
            if (!Services.VEIN_MINER.isToBreakEmpty()) {
                ci.cancel();
            }
        }*/
    }
}
