package net.smackplays.smacksutil.menus;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.smackplays.smacksutil.inventories.IBackpackInventory;
import net.smackplays.smacksutil.slots.BackpackSlot;
import net.smackplays.smacksutil.slots.BackpackUpgradeSlot;
import net.smackplays.smacksutil.slots.InvSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractLargeBackpackMenu extends AbstractContainerMenu {
    public final Container inventory;
    public final Inventory playerInventory;
    private final int rows = 9;
    private final int cols = 13;

    public AbstractLargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId);
        checkContainerSize(inv, rows * cols + 4);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inv.startOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        for (int j = 0; j < 4; ++j){
            this.addSlot(new BackpackUpgradeSlot(inv, j, 199, 21 + j * 18 - 3 - 3 * 18));
        }

        for (int j = 0; j < this.rows; ++j) {
            for (int k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inv, 4 + k + j * this.cols, -2 + k * 18 - 18 * 2, 21 + j * 18 - 3 - 3 * 18));
            }
        }

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new InvSlot(playerInventory, k + j * 9 + 9, - 2 + k * 18, 49 + j * 18 + i));
            }
        }

        for (int j = 0; j < 9; ++j) {
            this.addSlot(new InvSlot(playerInventory, j, - 2 + j * 18, 107 + i));
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
        IBackpackInventory impInv = (IBackpackInventory) inventory;
        NonNullList<ItemStack> items = impInv.getItems();
        List<ItemStack> temp = items.subList(4, rows * cols + 4);

        int maxStacksize = this.inventory.getMaxStackSize();

        for (int i = 0; i < temp.size(); i++){
            for(int j = 0; j < temp.size(); j++){
                if (i != j){
                    ItemStack stack1 = temp.get(i);
                    ItemStack stack2 = temp.get(j);
                    if (ItemStack.isSameItemSameTags(stack1, stack2) && stack1.getCount() + stack2.getCount() <= maxStacksize){
                        stack1.setCount(stack1.getCount() + stack2.getCount());
                        temp.set(i, stack1);
                        temp.set(j, Items.AIR.getDefaultInstance());
                    }
                }
            }
        }

        temp.sort(Comparator.comparing(this::getStringForSort));
        for (int i = 0; i < temp.size(); i++) {
            items.set(i + 4, temp.get(i));
            this.slots.get(i).set(impInv.getItem(i));
        }
    }

    private String getStringForSort(ItemStack stack) {
        String s;
        int maxStacksize = this.inventory.getMaxStackSize();
        if (stack.isEmpty()) {
            return "zzz" + stack.getItem().getDescriptionId() + stack.getCount();
        } else if (stack.hasCustomHoverName()) {
            s = stack.getHoverName().getString() + stack.getCount();
        } else if (stack.getCount() == maxStacksize){
            s = stack.getItem().getDescriptionId() + "00" + stack.getCount();
        }else {
            s = stack.getItem().getDescriptionId() + stack.getCount();
        }
        return s;
    }
}