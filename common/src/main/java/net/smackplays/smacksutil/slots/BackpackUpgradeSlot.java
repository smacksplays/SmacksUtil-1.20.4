package net.smackplays.smacksutil.slots;


import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.inventories.IBackpackInventory;
import net.smackplays.smacksutil.items.BackpackUpgradeItem;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BackpackUpgradeSlot extends Slot {
    public BackpackUpgradeSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        ItemStack stack = getItem();
        int offset = 1;
        ItemStack carried = player.containerMenu.getCarried();
        int carriedMultiplier;
        if (stack.getItem() instanceof BackpackUpgradeItem item){
            offset = item.getMultiplier();
        }
        if (carried.getItem() instanceof BackpackUpgradeItem item){
            carriedMultiplier = item.getMultiplier();
            if (offset < carriedMultiplier) return true;
            offset /= carriedMultiplier;
        }
        if (offset != 1){
            if (container instanceof BackpackInventory inv){
                return inv.checkRemoveUpgrade(inv.getMaxStackSize() / offset);
            }
            if (container instanceof IBackpackInventory inv){
                return inv.checkRemoveUpgrade(inv.getMaxStackSize() / offset);
            }
        }
        return super.mayPickup(player);
    }
    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof BackpackUpgradeItem;
    }

    @Override
    protected void onSwapCraft(int amount) {
        super.onSwapCraft(amount);
    }

    @Override
    public @NotNull ItemStack remove(int amount) {
        return super.remove(amount);
    }

    @Override
    public @NotNull ItemStack safeTake(int min, int max, @NotNull Player player) {
        return super.safeTake(min, max, player);
    }

    @Override
    public boolean allowModification(@NotNull Player player) {
        return super.allowModification(player);
    }
}
