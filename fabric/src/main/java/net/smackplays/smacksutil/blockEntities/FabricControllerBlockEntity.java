package net.smackplays.smacksutil.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.blocks.AbstractControllerBlock;

public class FabricControllerBlockEntity extends AbstractControllerBlockEntity {
    public FabricControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModClient.CONTROLLER_ENTITY_TYPE, pos, state);
    }

    public static FabricControllerBlockEntity create(BlockPos pos, BlockState state, AbstractControllerBlock block) {
        FabricControllerBlockEntity entity = new FabricControllerBlockEntity(pos, state);
        entity.createHelper(block, pos);
        return entity;
    }
}
