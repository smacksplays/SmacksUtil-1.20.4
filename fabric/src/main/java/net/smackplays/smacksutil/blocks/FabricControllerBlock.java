package net.smackplays.smacksutil.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.blockEntities.FabricControllerBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FabricControllerBlock extends AbstractControllerBlock {
    public static final MapCodec<FabricControllerBlock> CODEC = simpleCodec(FabricControllerBlock::new);

    public FabricControllerBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<FabricControllerBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return FabricControllerBlockEntity.create(blockPos, blockState, this);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(world, blockEntityType, ModClient.CONTROLLER_ENTITY_TYPE);
    }
}
