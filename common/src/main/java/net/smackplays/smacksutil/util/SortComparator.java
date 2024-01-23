package net.smackplays.smacksutil.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;

public class SortComparator implements Comparator<ItemStack> {
    public SortComparator(){

    }
    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        String name1 = Component.translatable(o1.getDescriptionId()).getString();
        int count1 = o1.getCount();
        String name2 = Component.translatable(o2.getDescriptionId()).getString();
        int count2 = o2.getCount();
        int t = Item.getId(o1.getItem());
        if (name1.equals("Air") && name2.equals("Air")) return 0;
        else if (name1.equals("Air")) return 1;
        else if (name2.equals("Air")) return -1;
        else{
            if (count1 == count2){
                // different names => compare Strings
                int comp1 = name1.compareTo(name2);
                return name1.compareTo(name2);
            } else {
                // same name compare count
                if (count1 < count2) return 1;
                else return -1;
            }
        }
    }

    private String getStringForSort(ItemStack stack) {
        if (stack.isEmpty()) {
            return "z" + Component.translatable(stack.getDescriptionId()).getString();
        }else {
            return ";";
        }
    }
}
