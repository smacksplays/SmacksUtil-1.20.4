package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.ImplementedInventory;

public class EnchantingToolMenu extends AbstractEnchantingToolMenu {

    public EnchantingToolMenu(int syncId, Inventory playerInv, Container inv) {
        super(SmacksUtil.ENCHANTING_TOOL.get(), syncId, playerInv, inv);
    }

    public static EnchantingToolMenu create(int syncId, Inventory playerInventory) {
        return new EnchantingToolMenu(syncId, playerInventory, ImplementedInventory.ofSize(1));
    }
}