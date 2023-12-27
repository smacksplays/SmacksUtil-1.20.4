package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    private final ImplementedInventory backpackInventory; // Chest inventory
    private static final int INVENTORY_SIZE = 54; // 6 rows * 9 cols
    private static int selected_slot;

    protected BackpackScreenHandler(int syncId, ImplementedInventory backpackInventory, PlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X6, syncId); // Since we didn't create a ScreenHandlerType, we will place null here.
        this.backpackInventory = backpackInventory;
        checkSize(backpackInventory, INVENTORY_SIZE);
        backpackInventory.onOpen(player);
        selected_slot = player.getInventory().selectedSlot;

        // Creating Slots for GUI. A Slot is essentially a corresponding from inventory ItemStacks to the GUI position.
        int i;
        int j;

        // Chest Inventory
        for (i = 0; i < 6; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(new BackpackSlot(backpackInventory, i * 9 + j, 8 + j * 18, 18 + i * 18));
            }
        }

        // Player Inventory (27 storage + 9 hotbar)
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(new BackpackSlot(player.getInventory(), i * 9 + j + 9, 8 + j * 18, 18 + i * 18 + 103 + 18));
            }
        }


        for (j = 0; j < 9; j++) {
            this.addSlot(new BackpackSlot(player.getInventory(), j, 8 + j * 18, 18 + 161 + 18));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 6 * 9 ? !this.insertItem(itemStack2, 6 * 9, this.slots.size(), true) : !this.insertItem(itemStack2, 0, 6 * 9, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.backpackInventory.canPlayerUse(player);
    }

    @Override
    public boolean isValid(int slot) {
        return super.isValid(slot);
    }
}