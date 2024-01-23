package net.smackplays.smacksutil.slots;


import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackSlot extends Slot {
    public BackpackSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return super.mayPickup(player);
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

    @Override
    public int getMaxStackSize() {
        return Math.min(64 * 8 * 8 * 8 * 8, this.container.getMaxStackSize());
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return Math.min(64 * 8 * 8 * 8 * 8, this.container.getMaxStackSize());
    }
}
