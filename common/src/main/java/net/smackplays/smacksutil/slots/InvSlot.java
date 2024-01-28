package net.smackplays.smacksutil.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.inventories.LargeBackpackInventory;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import net.smackplays.smacksutil.menus.AbstractLargeBackpackMenu;

public class InvSlot extends Slot {
    public InvSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }
    @Override
    public boolean mayPickup(Player player) {
        if (player.containerMenu instanceof AbstractBackpackMenu menu){
            BackpackInventory inv = (BackpackInventory) menu.inventory;
            if (ItemStack.isSameItemSameTags(this.getItem(), inv.stack)) return false;
        } else if (player.containerMenu instanceof AbstractLargeBackpackMenu menu){
            LargeBackpackInventory inv = (LargeBackpackInventory) menu.inventory;
            if (ItemStack.isSameItemSameTags(this.getItem(), inv.stack)) return false;
        }
        return super.mayPickup(player);
    }
}
