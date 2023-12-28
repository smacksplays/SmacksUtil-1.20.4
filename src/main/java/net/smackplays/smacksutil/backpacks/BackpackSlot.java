package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BackpackSlot extends Slot {
    private final int index;
    public BackpackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.index = index;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        int sel = playerEntity.getInventory().selectedSlot;
        ItemStack s = playerEntity.getInventory().main.get(sel);
        if (this.getStack().equals(s)){
            return false;
        }
        return super.canTakeItems(playerEntity);
    }

    @Override
    protected void onTake(int amount) {
        super.onTake(amount);
    }
}
