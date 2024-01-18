package net.smackplays.smacksutil.slots;


import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.inventories.IBackpackInventory;
import net.smackplays.smacksutil.items.BackpackUpgradeTier1Item;
import net.smackplays.smacksutil.items.BaseBackpackUpgradeItem;
import org.jetbrains.annotations.NotNull;

public class BackpackUpgradeSlot extends Slot {
    public BackpackUpgradeSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPickup(Player player) {
        int sel = player.getInventory().selected;
        ItemStack s = player.getInventory().items.get(sel);
        container.getMaxStackSize();
        if (container instanceof BackpackInventory inv){
            return inv.checkRemoveUpgrade(inv.getMaxStackSize() / 2);
        }
        if (container instanceof IBackpackInventory inv){
            return inv.checkRemoveUpgrade(inv.getMaxStackSize() / 2);
        }
        return super.mayPickup(player);
    }
    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof BaseBackpackUpgradeItem;
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
