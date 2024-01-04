package net.smackplays.smacksutil.backpacks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;

public class LargeBackpackScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final Inventory playerInventory;
    private final int rows = 9;
    private final int cols = 13;

    public LargeBackpackScreenHandler(int syncId, Inventory playerInv, Container inv) {
        super(SmacksUtil.GENERIC_13X9, syncId);
        checkContainerSize(inv, rows * cols);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inv.startOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int j;
        int k;
        for (j = 0; j < this.rows; ++j) {
            for (k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inv, k + j * this.cols, 8 + k * 18 - 18 * 2, 18 + j * 18 - 3 - 3 * 18));
            }
        }

        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new BackpackSlot(playerInventory, k + j * 9 + 9, 8 + k * 18, 46 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlot(new BackpackSlot(playerInventory, j, 8 + j * 18, 104 + i));
        }
    }

    public static LargeBackpackScreenHandler createGeneric13x9(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new LargeBackpackScreenHandler(syncId, playerInventory, ImplementedInventory.ofSize(13 * 9));
    }

    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index < this.rows * this.cols) {
                if (!this.moveItemStackTo(itemStack2, this.rows * this.cols, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.rows * this.cols, false)) {
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
}