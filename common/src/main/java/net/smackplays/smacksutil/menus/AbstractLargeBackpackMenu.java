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
import net.smackplays.smacksutil.util.SortComparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            if (index < this.rows * this.cols + 4) {
                if (this.moveItemStack(itemStack2, this.rows * this.cols + 4, this.slots.size(), true, 64)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.moveItemStack(itemStack2, 4, this.rows * this.cols + 4, false, inventory.getMaxStackSize())) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        if (getCarried().isEmpty()) return ItemStack.EMPTY;
        return itemStack;
    }

    protected boolean moveItemStack(ItemStack stack, int min, int max, boolean backwards, int maxStackSize) {
        boolean bl1 = false;
        int counter = min;
        if (backwards) {
            counter = max - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty() && (backwards ? counter >= min : counter < max)) {
                Slot slot = this.slots.get(counter);
                ItemStack stack1 = slot.getItem();
                if (!stack1.isEmpty() && ItemStack.isSameItemSameComponents(stack, stack1)) {
                    int combinedCount = stack1.getCount() + stack.getCount();
                    if (combinedCount <= maxStackSize) {
                        stack.setCount(0);
                        stack1.setCount(combinedCount);
                        slot.setChanged();
                        bl1 = true;
                    } else if (stack1.getCount() < maxStackSize) {
                        stack.shrink(maxStackSize - stack1.getCount());
                        stack1.setCount(maxStackSize);
                        slot.setChanged();
                        bl1 = true;
                    }
                }

                if (backwards) {
                    --counter;
                } else {
                    ++counter;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (backwards) {
                counter = max - 1;
            } else {
                counter = min;
            }

            while(backwards ? counter >= min : counter < max) {
                Slot slot = this.slots.get(counter);
                ItemStack stack1 = slot.getItem();
                int combinedCount = stack1.getCount() + stack.getCount();
                if (stack1.isEmpty() && slot.mayPlace(stack)) {
                    if (stack.getCount() > slot.getMaxStackSize()) {
                        slot.setByPlayer(stack.split(slot.getMaxStackSize()));
                    } else if (stack.getCount() > 1 && !stack.isStackable()) {
                        slot.setByPlayer(stack.split(1));
                    }else {
                        slot.setByPlayer(stack.split(stack.getCount()));
                    }

                    slot.setChanged();
                    bl1 = true;
                    break;
                } else if (ItemStack.isSameItemSameComponents(stack, stack1) && slot instanceof BackpackSlot && combinedCount < maxStackSize){
                    stack1.grow(stack.getCount());
                    stack.setCount(0);
                }

                if (backwards) {
                    --counter;
                } else {
                    ++counter;
                }
            }
        }

        return !bl1;
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
                    if (ItemStack.isSameItemSameComponents(stack1, stack2) && stack1.getCount() + stack2.getCount() <= maxStacksize){
                        stack1.setCount(stack1.getCount() + stack2.getCount());
                        temp.set(i, stack1);
                        temp.set(j, Items.AIR.getDefaultInstance());
                    }
                }
            }
        }

        temp.sort(new SortComparator());
        for (int i = 0; i < temp.size(); i++) {
            items.set(i + 4, temp.get(i));
            this.slots.get(i).set(impInv.getItem(i));
        }
    }
}