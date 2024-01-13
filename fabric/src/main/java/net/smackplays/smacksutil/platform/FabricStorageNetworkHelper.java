package net.smackplays.smacksutil.platform;

import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.platform.services.IStorageNetworkHelper;

public class FabricStorageNetworkHelper implements IStorageNetworkHelper {
    @Override
    public boolean isNetworkBlock(BlockState blockState) {
        return isItemMonitor(blockState)
                || isDiskReader(blockState);
    }

    @Override
    public boolean isController(BlockState blockState) {
        return blockState.is(ModClient.CONTROLLER_BLOCK);
    }

    public boolean isItemMonitor(BlockState blockState) {
        return blockState.is(ModClient.ITEM_MONITOR_BLOCK);
    }

    public boolean isDiskReader(BlockState blockState) {
        return blockState.is(ModClient.DISK_READER_BLOCK);
    }
}
