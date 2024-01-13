package net.smackplays.smacksutil.items;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DiskItem extends Item {

    protected NonNullList<ItemStack> items = NonNullList.withSize(64, ItemStack.EMPTY);

    public DiskItem(Properties props) {
        super(props);
    }

    public boolean addItemStack(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(stack)) {
                ItemStack s = items.get(i);
                s.setCount(s.getCount() + stack.getCount());
                return true;
            }
            if (items.get(i).equals(ItemStack.EMPTY)) {
                items.set(i, stack);
                return true;
            }
        }
        return false;
    }

    public boolean removeItemStack(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(stack)) {
                ItemStack s = items.get(i);
                if (s.getCount() - stack.getCount() < 0) {
                    return false;
                } else if (s.getCount() - stack.getCount() == 0) {
                    items.set(i, ItemStack.EMPTY);
                    return true;
                } else {
                    s.setCount(s.getCount() - stack.getCount());
                    return true;
                }
            }
        }
        return false;
    }

}
