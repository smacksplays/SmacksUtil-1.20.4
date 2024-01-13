package net.smackplays.smacksutil.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ItemManagerSlot extends Slot {
    public ItemManagerSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return true;
    }

    @Override
    public boolean mayPickup(Player player) {
        int sel = player.getInventory().selected;
        ItemStack s = player.getInventory().items.get(sel);
        if (this.getItem().equals(s)) {
            return false;
        }
        return super.mayPickup(player);
    }

    @Override
    protected void onSwapCraft(int amount) {
        super.onSwapCraft(amount);
    }

    @Override
    public ItemStack remove(int amount) {
        return super.remove(amount);
    }

    @Override
    public ItemStack safeTake(int min, int max, Player player) {
        return super.safeTake(min, max, player);
    }

    @Override
    public boolean allowModification(Player player) {
        return super.allowModification(player);
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return Integer.MAX_VALUE;
    }
}
