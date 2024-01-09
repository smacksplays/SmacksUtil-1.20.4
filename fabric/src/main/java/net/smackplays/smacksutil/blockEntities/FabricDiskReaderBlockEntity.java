package net.smackplays.smacksutil.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.FabricDiskReaderMenu;

public class FabricDiskReaderBlockEntity extends AbstractDiskReaderBlockEntity {
    public FabricDiskReaderBlockEntity(BlockPos pos, BlockState state) {
        super(ModClient.DISK_READER_ENTITY_TYPE, pos, state);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("container.disk_reader");
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new FabricDiskReaderMenu(i, inventory, this);
    }
}
