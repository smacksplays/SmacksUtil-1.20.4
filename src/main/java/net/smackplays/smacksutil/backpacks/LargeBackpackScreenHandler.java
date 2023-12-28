package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.SmacksUtil;

public class LargeBackpackScreenHandler extends ScreenHandler
{
    private final Inventory inventory;
    private final int rows = 9;
    private final int cols = 13;

    public static LargeBackpackScreenHandler createGeneric13x9(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        return new LargeBackpackScreenHandler(syncId, playerInventory, ImplementedInventory.ofSize(13 * 9));
    }

    public LargeBackpackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(SmacksUtil.GENERIC_13X9, syncId);
        checkSize(inventory, rows * cols);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int j;
        int k;
        for(j = 0; j < this.rows; ++j) {
            for(k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inventory, k + j * 9, 8 + k * 18 - 18 * 2, 18 + j * 18));
            }
        }

        for(j = 0; j < 3; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new BackpackSlot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new BackpackSlot(playerInventory, j, 8 + j * 18, 161 + i));
        }

    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * 9) {
                if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

}