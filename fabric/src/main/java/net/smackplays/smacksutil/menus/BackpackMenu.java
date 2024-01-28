package net.smackplays.smacksutil.menus;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        ItemStack backpack = playerInventory.getSelected();

        NonNullList<Slot> slots = playerInventory.player.inventoryMenu.slots;
        for (int i = slots.size() - 1; i >= 0; i--){
            if (slots.get(i).getItem().is(SmacksUtil.BACKPACK_ITEM)){
                backpack = slots.get(i).getItem();
                break;
            }
        }
        return new BackpackMenu(SmacksUtil.BACKPACK_MENU, syncId, playerInventory, new BackpackInventory(backpack));
    }
}