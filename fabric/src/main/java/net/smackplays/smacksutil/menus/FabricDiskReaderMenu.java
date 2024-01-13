package net.smackplays.smacksutil.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.backpacks.ImplementedInventory;
import net.smackplays.smacksutil.slots.DiskReaderSlot;

public class FabricDiskReaderMenu extends AbstractContainerMenu {
    private final Container inventory;
    private final Inventory playerInventory;

    public FabricDiskReaderMenu(int i, Inventory playerInv, Container inv) {
        super(SmacksUtil.DISK_READER_MENU, i);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inv.startOpen(playerInventory.player);

        int j;
        int k;

        int y_offset = 9;
        for (j = 0; j < 4; ++j) {
            for (k = 0; k < 2; ++k) {
                this.addSlot(new DiskReaderSlot(inv, k + j * 2, 62 + 36 * k, y_offset + j * 18));
            }
        }
        y_offset = 94;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, y_offset + j * 18));
            }
        }
        y_offset = 152;
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, y_offset));
        }
    }

    public static FabricDiskReaderMenu create(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new FabricDiskReaderMenu(syncId, playerInventory, ImplementedInventory.ofSize(8));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index < 4 * 2) {
                if (!this.moveItemStackTo(itemStack2, 4 * 2, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, 4 * 2, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }
}
