package net.smackplays.smacksutil.inventories;


import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantmentToolInventory implements IEnchantmentToolInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public EnchantmentToolInventory(ItemStack stack) {
        this.stack = stack;
        CompoundTag tag = stack.getTagElement("enchantment_tool");

        if (tag != null) {
            ContainerHelper.loadAllItems(tag, items);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CompoundTag tag = stack.getOrCreateTagElement("enchantment_tool");
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return IEnchantmentToolInventory.super.getItem(slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }
}