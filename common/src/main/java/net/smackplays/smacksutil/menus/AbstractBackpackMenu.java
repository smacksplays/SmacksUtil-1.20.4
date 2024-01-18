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

public class AbstractBackpackMenu extends AbstractContainerMenu {
    public final Container inventory;
    public final Inventory playerInventory;
    private final int rows = 6;
    private final int cols = 9;

    public AbstractBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId);
        checkContainerSize(inv, rows * cols + 4);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inv.startOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;


        for (int j = 0; j < 4; ++j){
            this.addSlot(new BackpackUpgradeSlot(inv, j, 163, 18 + j * 18 - 9 * 3));
        }

        for (int j = 0; j < this.rows; ++j) {
            for (int k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inv, 4 + k + j * this.cols, -2 + k * 18, 18 + j * 18 - 9 * 3));
            }
        }

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new InvSlot(playerInventory, k + j * 9 + 9, -2 + k * 18, 76 + j * 18 + i));
            }
        }

        for (int j = 0; j < 9; ++j) {
            this.addSlot(new InvSlot(playerInventory, j, -2 + j * 18, 134 + i));
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
                if (!this.moveItemStackTo(itemStack2, this.rows * this.cols, this.slots.size(), true, 64)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 4, this.rows * this.cols + 4, false, inventory.getMaxStackSize())) {
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

    protected boolean moveItemStackTo(ItemStack stack, int min, int max, boolean bl, int maxStackSize) {
        boolean bl1 = false;
        int $$5 = min;
        if (bl) {
            $$5 = max - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty() && (bl ? $$5 >= min : $$5 < max)) {
                Slot slot = this.slots.get($$5);
                ItemStack stack1 = slot.getItem();
                if (!stack1.isEmpty() && ItemStack.isSameItemSameTags(stack, stack1)) {
                    int $$8 = stack1.getCount() + stack.getCount();
                    if ($$8 <= maxStackSize) {
                        stack.setCount(0);
                        stack1.setCount($$8);
                        slot.setChanged();
                        bl1 = true;
                    } else if (stack1.getCount() < maxStackSize) {
                        stack.shrink(maxStackSize - stack1.getCount());
                        stack1.setCount(maxStackSize);
                        slot.setChanged();
                        bl1 = true;
                    }
                }

                if (bl) {
                    --$$5;
                } else {
                    ++$$5;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (bl) {
                $$5 = max - 1;
            } else {
                $$5 = min;
            }

            while(bl ? $$5 >= min : $$5 < max) {
                Slot $$9 = this.slots.get($$5);
                ItemStack $$10 = $$9.getItem();
                if ($$10.isEmpty() && $$9.mayPlace(stack)) {
                    if (stack.getCount() > $$9.getMaxStackSize()) {
                        $$9.setByPlayer(stack.split($$9.getMaxStackSize()));
                    } else {
                        $$9.setByPlayer(stack.split(stack.getCount()));
                    }

                    $$9.setChanged();
                    bl1 = true;
                    break;
                }

                if (bl) {
                    --$$5;
                } else {
                    ++$$5;
                }
            }
        }

        return bl1;
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
