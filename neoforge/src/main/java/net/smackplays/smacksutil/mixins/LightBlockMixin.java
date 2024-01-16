package net.smackplays.smacksutil.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.SmacksUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightBlock.class)
public class LightBlockMixin {

    @Inject(at = @At("HEAD"), method = "getShape", cancellable = true)
    private void getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (pos.equals(new BlockPos(0, 0, 0))) return;
        cir.setReturnValue(context.isHoldingItem(Items.LIGHT)
                || context.isHoldingItem(SmacksUtil.LIGHT_WAND.get())
                || context.isHoldingItem(SmacksUtil.AUTO_LIGHT_WAND.get()) ? Shapes.block() : Shapes.empty());
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (level.isClientSide) return;
        if (player == null) return;
        if (player.getItemInHand(interactionHand).is(SmacksUtil.LIGHT_WAND.get())
                || player.getItemInHand(interactionHand).is(SmacksUtil.AUTO_LIGHT_WAND.get())) {
            if (level.getBlockState(blockPos).is(Blocks.LIGHT)) {
                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
