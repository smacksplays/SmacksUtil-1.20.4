package net.smackplays.smacksutil.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.blocks.AbstractControllerBlock;
import net.smackplays.smacksutil.storagenetwork.NetworkHelper;

public class AbstractControllerBlockEntity extends BlockEntity {
    static int counter = 0;
    NetworkHelper helper;

    public AbstractControllerBlockEntity(BlockEntityType<?> $$0, BlockPos pos, BlockState state) {
        super($$0, pos, state);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, AbstractControllerBlockEntity entity) {
        if (entity.helper == null) return;
        if (counter == 10) {
            entity.helper.updateNetwork(world, pos);
            counter = 0;
        }
        counter++;
    }

    public void createHelper(AbstractControllerBlock block, BlockPos pos) {
        helper = new NetworkHelper(pos, block);
    }
}
