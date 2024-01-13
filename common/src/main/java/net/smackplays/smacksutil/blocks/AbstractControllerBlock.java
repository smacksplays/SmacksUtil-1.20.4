package net.smackplays.smacksutil.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.blockEntities.AbstractControllerBlockEntity;

import javax.annotation.Nullable;

public abstract class AbstractControllerBlock extends BaseEntityBlock {
    protected AbstractControllerBlock(Properties props) {
        super(props);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(
            Level $$0, BlockEntityType<T> $$1, BlockEntityType<? extends AbstractControllerBlockEntity> $$2
    ) {
        return $$0.isClientSide ? null : createTickerHelper($$1, $$2, AbstractControllerBlockEntity::serverTick);
    }

    @Override
    protected abstract MapCodec<? extends AbstractControllerBlock> codec();

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean bool) {
        world.updateNeighborsAt(pos, state.getBlock());
        super.onRemove(state, world, pos, newState, bool);
    }
}