package net.smackplays.smacksutil.storagenetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.blockEntities.AbstractDiskReaderBlockEntity;
import net.smackplays.smacksutil.blockEntities.AbstractItemMonitorBlockEntity;
import net.smackplays.smacksutil.blocks.AbstractControllerBlock;
import net.smackplays.smacksutil.platform.Services;

import java.util.ArrayList;

public class NetworkHelper {

    AbstractControllerBlock controller;
    BlockPos controllerPos;

    public NetworkHelper(BlockPos pos, AbstractControllerBlock controller) {
        this.controller = controller;
        this.controllerPos = pos;
    }

    public void updateNetwork(Level world, BlockPos pos) {
        ArrayList<BlockPos> neighbors = getControllerNeighbors(world, controllerPos);
        ArrayList<AbstractItemMonitorBlockEntity> itemMonitors = new ArrayList<>();
        ArrayList<AbstractDiskReaderBlockEntity> diskReaders = new ArrayList<>();
        for (BlockPos p : neighbors) {
            if (Services.NETWORK_HELPER.isItemMonitor(world.getBlockState(p))) {
                itemMonitors.add((AbstractItemMonitorBlockEntity) world.getBlockEntity(p));
            } else if (Services.NETWORK_HELPER.isDiskReader(world.getBlockState(p))) {
                diskReaders.add((AbstractDiskReaderBlockEntity) world.getBlockEntity(p));
            }
        }
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        for (AbstractDiskReaderBlockEntity b : diskReaders) {
            itemStacks.addAll(b.getItems());
        }
        for (AbstractItemMonitorBlockEntity b : itemMonitors) {
            b.setItems(itemStacks);
        }
    }

    public ArrayList<BlockPos> getControllerNeighbors(Level world, BlockPos pos) {
        ArrayList<BlockPos> list = new ArrayList<>();
        if (Services.NETWORK_HELPER.isController(world.getBlockState(pos))) {
            ArrayList<BlockPos> toCheck = new ArrayList<>() {{
                add(pos.above());
                add(pos.below());
                add(pos.north());
                add(pos.south());
                add(pos.west());
                add(pos.east());
            }};

            for (BlockPos pos1 : toCheck) {
                if (Services.NETWORK_HELPER.isNetworkBlock(world.getBlockState(pos1))) {
                    list.add(pos1);
                }
            }
        }
        return list;
    }
}
