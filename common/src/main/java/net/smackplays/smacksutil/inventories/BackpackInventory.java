package net.smackplays.smacksutil.inventories;


import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import org.jetbrains.annotations.NotNull;

public class BackpackInventory implements IBackpackInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9 * 6 + 4, ItemStack.EMPTY);

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

    @Override
    public boolean canPlaceItem(int $$0, ItemStack $$1) {
        return IBackpackInventory.super.canPlaceItem($$0, $$1);
    }

    @Override
    public boolean canTakeItem(Container $$0, int $$1, ItemStack $$2) {
        return IBackpackInventory.super.canTakeItem($$0, $$1, $$2);
    }
}