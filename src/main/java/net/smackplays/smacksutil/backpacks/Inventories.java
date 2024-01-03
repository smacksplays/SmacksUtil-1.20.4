package net.smackplays.smacksutil.backpacks;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class Inventories {
    public Inventories() {
    }

    public static ItemStack splitStack(List<ItemStack> stacks, int slot, int amount) {
        return slot >= 0 && slot < stacks.size() && !(stacks.get(slot)).isEmpty() && amount > 0 ? (stacks.get(slot)).split(amount) : ItemStack.EMPTY;
    }

    public static ItemStack removeStack(List<ItemStack> stacks, int slot) {
        return slot >= 0 && slot < stacks.size() ? stacks.set(slot, ItemStack.EMPTY) : ItemStack.EMPTY;
    }

    public static CompoundTag writeNbt(CompoundTag nbt, NonNullList<ItemStack> stacks) {
        return writeNbt(nbt, stacks, true);
    }

    public static CompoundTag writeNbt(CompoundTag nbt, NonNullList<ItemStack> stacks, boolean setIfEmpty) {
        ListTag nbtList = new ListTag();

        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.get(i);
            if (!itemStack.isEmpty()) {
                CompoundTag nbtCompound = new CompoundTag();
                nbtCompound.putByte("Slot", (byte) i);
                itemStack.setTag(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        if (!nbtList.isEmpty() || setIfEmpty) {
            nbt.put("Items", nbtList);
        }

        return nbt;
    }

    public static void readNbt(CompoundTag nbt, NonNullList<ItemStack> stacks) {
        ListTag nbtList = nbt.getList("Items", 10);

        for (int i = 0; i < nbtList.size(); ++i) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j < stacks.size()) {
                stacks.set(j, ItemStack.of(nbtCompound));
            }
        }

    }

    public static int remove(Inventory inventory, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
        int i = 0;

        for (int j = 0; j < inventory.getContainerSize(); ++j) {
            ItemStack itemStack = inventory.getItem(j);
            int k = remove(itemStack, shouldRemove, maxCount - i, dryRun);
            if (k > 0 && !dryRun && itemStack.isEmpty()) {
                inventory.setItem(j, ItemStack.EMPTY);
            }

            i += k;
        }

        return i;
    }

    public static int remove(ItemStack stack, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
        if (!stack.isEmpty() && shouldRemove.test(stack)) {
            if (dryRun) {
                return stack.getCount();
            } else {
                int i = maxCount < 0 ? stack.getCount() : Math.min(maxCount, stack.getCount());
                stack.shrink(i);
                return i;
            }
        } else {
            return 0;
        }
    }
}

