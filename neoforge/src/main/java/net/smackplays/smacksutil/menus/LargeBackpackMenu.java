package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.IBackpackInventory;
import org.jetbrains.annotations.Nullable;

public class LargeBackpackMenu extends AbstractLargeBackpackMenu {

    public LargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static LargeBackpackMenu createGeneric13x9(int syncId, Inventory playerInventory) {
        return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU.get(), syncId, playerInventory, IBackpackInventory.ofSize(13 * 9 + 4));
    }
}