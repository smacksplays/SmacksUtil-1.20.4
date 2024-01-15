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

public class LargeBackpackMenu extends AbstractLargeBackpackMenu {

    public LargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    public static LargeBackpackMenu createGeneric13x9(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new LargeBackpackMenu(SmacksUtil.GENERIC_13X9.get(), syncId, playerInventory, ImplementedInventory.ofSize(13 * 9));
    }
}