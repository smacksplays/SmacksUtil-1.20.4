package net.smackplays.smacksutil.backpacks;


import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BackpackInventory implements ImplementedInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9 * 6, ItemStack.EMPTY);

    BackpackInventory(ItemStack stack) {
        this.stack = stack;
        CompoundTag tag = stack.getTagElement("backpack");

        if (tag != null) {
            Inventories.readNbt(tag, items);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CompoundTag tag = stack.getOrCreateTagElement("backpack");
        Inventories.writeNbt(tag, items);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }
}