package net.smackplays.smacksutil.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.ImplementedInventory;
import net.smackplays.smacksutil.slots.BackpackSlot;
import org.jetbrains.annotations.Nullable;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new BackpackMenu(SmacksUtil.GENERIC_9X6.get(), syncId, playerInventory, ImplementedInventory.ofSize(13 * 9));
    }
}