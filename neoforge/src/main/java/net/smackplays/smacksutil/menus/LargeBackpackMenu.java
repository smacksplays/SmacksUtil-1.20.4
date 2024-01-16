package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.ImplementedInventory;
import org.jetbrains.annotations.Nullable;

public class LargeBackpackMenu extends AbstractLargeBackpackMenu {

    public LargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static LargeBackpackMenu createGeneric13x9(int syncId, Inventory playerInventory) {
        return new LargeBackpackMenu(SmacksUtil.GENERIC_13X9.get(), syncId, playerInventory, ImplementedInventory.ofSize(13 * 9));
    }
}