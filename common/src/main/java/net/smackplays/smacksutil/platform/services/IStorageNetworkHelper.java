package net.smackplays.smacksutil.platform.services;

import net.minecraft.world.level.block.state.BlockState;

public interface IStorageNetworkHelper {
    boolean isNetworkBlock(BlockState blockState);

    boolean isController(BlockState blockState);

    boolean isItemMonitor(BlockState blockState);

    boolean isDiskReader(BlockState blockState);

}
