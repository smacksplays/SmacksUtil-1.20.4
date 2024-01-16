package net.smackplays.smacksutil.menus;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.ImplementedInventory;
import net.smackplays.smacksutil.slots.BackpackSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public abstract class AbstractLargeBackpackMenu extends AbstractContainerMenu {
    public final Container inventory;
    public final Inventory playerInventory;
    private final int rows = 9;
    private final int cols = 13;

    public AbstractLargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId);
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

    public boolean stillValid(@NotNull Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
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

    public void sort() {
        ImplementedInventory impInv = (ImplementedInventory) inventory;
        NonNullList<ItemStack> items = impInv.getItems();
        items.sort(Comparator.comparing(this::getStringForSort));
        for (int i = 0; i < items.size(); i++) {
            this.slots.get(i).set(impInv.getItem(i));
        }
    }

    private String getStringForSort(ItemStack stack) {
        String s;
        if (stack.isEmpty()) {
            return "zzz";
        } else if (stack.hasCustomHoverName()) {
            s = stack.getHoverName().getString();
        } else {
            s = stack.getDisplayName().getString();
        }
        return s;
    }
}