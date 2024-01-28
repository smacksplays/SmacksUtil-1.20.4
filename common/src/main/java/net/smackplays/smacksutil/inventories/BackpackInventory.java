package net.smackplays.smacksutil.inventories;


import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackInventory implements IBackpackInventory {
    public final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9 * 6 + 4, ItemStack.EMPTY);

    public BackpackInventory(){
        this.stack = ItemStack.EMPTY;
        CompoundTag tag = stack.getTagElement("backpack");

        if (tag != null) {
            loadAllItems(tag, items);
        }
    }

    public BackpackInventory(ItemStack stack) {
        this.stack = stack;
        CompoundTag tag = stack.getTagElement("backpack");

        if (tag != null) {
            loadAllItems(tag, items);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CompoundTag tag = stack.getOrCreateTagElement("backpack");
        saveAllItems(tag, items, true);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }
}