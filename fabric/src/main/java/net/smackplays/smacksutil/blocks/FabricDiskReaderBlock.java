package net.smackplays.smacksutil.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.blockEntities.FabricDiskReaderBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FabricDiskReaderBlock extends AbstractDiskReaderBlock{
    public static final MapCodec<FabricDiskReaderBlock> CODEC = simpleCodec(FabricDiskReaderBlock::new);

    public FabricDiskReaderBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<FabricDiskReaderBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FabricDiskReaderBlockEntity(blockPos, blockState);
    }
}
