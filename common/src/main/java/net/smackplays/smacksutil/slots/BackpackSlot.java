package net.smackplays.smacksutil.slots;


import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackSlot extends Slot {
    public BackpackSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
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
}
