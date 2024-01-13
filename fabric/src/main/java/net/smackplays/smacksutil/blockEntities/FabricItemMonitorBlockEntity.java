package net.smackplays.smacksutil.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.FabricItemMonitorMenu;

public class FabricItemMonitorBlockEntity extends AbstractDiskReaderBlockEntity {
    public FabricItemMonitorBlockEntity(BlockPos pos, BlockState state) {
        super(ModClient.ITEM_MONITOR_ENTITY_TYPE, pos, state);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("container.item_monitor");
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new FabricItemMonitorMenu(i, inventory, this, ContainerLevelAccess.NULL);
    }
}
