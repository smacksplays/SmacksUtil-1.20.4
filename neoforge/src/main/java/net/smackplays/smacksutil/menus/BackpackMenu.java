package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.inventories.IBackpackInventory;
import org.jetbrains.annotations.Nullable;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory) {
        return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(playerInventory.getSelected()));
    }
}