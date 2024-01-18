package net.smackplays.smacksutil.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InvSlot extends Slot {
    public InvSlot(Container inventory, int index, int x, int y) {
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
}
